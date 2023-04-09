package com.lynjava.ddd.test.spring.extend;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ThirdPartyServiceRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourcePatternResolver resourcePatternResolver;

    private CachingMetadataReaderFactory metadataReaderFactory;

    private static final String PACKAGE_SEPARATOR = ".";

    private static final String PATH_SEPARATOR = "/";

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 1、获取 ThirdPartyServiceScan 注解属性 Map
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(ThirdPartyServiceScan.class.getName()));
        if (attributes == null) {
            return;
        }

        log.info("================= 扫描标注 ThirdPartyService 注解的接口开始 =================");
        // 2、扫描并获取标注 ThirdPartyService 注解接口元信息
        Set<MetadataReader> thirdPartyServices = scanThirdPartyService(attributes, importingClassMetadata);
        for (MetadataReader thirdPartyService : thirdPartyServices) {
            // 3、将每个接口动态代理生成代理类，并将代理类注册到 IOC 容器中
            registerThirdPartyService(thirdPartyService, registry);
        }
        log.info("================= 扫描标注 ThirdPartyService 注解的接口结束 =================");
    }

    /**
     * 将扫描到的接口通过 FactoryBean 生成其实现类，并将实现类注入到 IOC 容器中
     *
     * @param thirdPartyService 接口元数据
     * @param registry          BeanDefinitionRegistry
     */
    private void registerThirdPartyService(MetadataReader thirdPartyService, BeanDefinitionRegistry registry) {
        String beanName = thirdPartyService.getClassMetadata().getClassName();
        // 3.1 通过 BeanDefinitionBuilder 构建一个 BeanDefinition，genericBeanDefinition 需要的入参为 Bean 的名称，
        // 此处使用接口名称
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanName);
        // 3.2 此处可以为构建 Bean 实例传递必要的参数，此处通过构造器传参，使用 addConstructorArgValue 方法，如果通过 Setter
        // 方法传参，则使用 addPropertyValue 方法。
        builder.addConstructorArgValue(thirdPartyService);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        // 3.3 此处需要注意，如果添加的是普通的类，那么就直接创建该类的实例，但是如果传入的是 FactoryBean 接口的实例，那么就会
        // 调用 getObject 方法获取需要注入的实例对象。
        beanDefinition.setBeanClass(ThirdPartyServiceFactoryBean.class);
        // 3.4 将 BeanDefinition 添加到注册器中，Spring 会更加 BeanDefinition 构建 Bean 的实例，并添加到 IOC 容器中
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 扫描 {@link ThirdPartyService} 注解标注的接口
     *
     * @param attributes             {@link ThirdPartyServiceScan#value()}
     * @param importingClassMetadata {@link ThirdPartyServiceScan} 注解标注类的元信息
     * @return 接口类别
     */
    private Set<MetadataReader> scanThirdPartyService(AnnotationAttributes attributes,
                                                      AnnotationMetadata importingClassMetadata) {
        // 2.1 获取扫描的包路径，优先获取 ThirdPartyServiceScan 注解 value 的值，如果数组为空，则获取标注该注解的类的包路径
        final List<String> scanPackages =
                getScanPackages(attributes, ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        log.info("待扫描的包路径为：{}", scanPackages);
        final Set<MetadataReader> result = new LinkedHashSet<>();
        for (String scanPackage : scanPackages) {
            // 2.2 对每个包路径即子包路径下的类进行扫描，获取标注了 ThirdPartyService 数据的接口元信息
            result.addAll(scanThirdPartyService(scanPackage));
        }
        return result;
    }

    @SneakyThrows
    private Set<MetadataReader> scanThirdPartyService(String scanPackage) {
        final Set<MetadataReader> result = new LinkedHashSet<>();
        // 2.2.1 将包路径转换为：classpath*:com/jub/xxx/**/*.class，如果只需要配当前包下的类，那么吧 ** 去掉即可
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                convertPackageToResourcePath(scanPackage) + '/' + DEFAULT_RESOURCE_PATTERN;
        // 2.2.2 获取包路径对应的资源信息，也就是对应的类文件信息
        Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
        for (Resource resource : resources) {
            // 2.2.3 将类文件流转换为类的元数据，方便获取类信息，如类的元数据、标注注解的元数据
            MetadataReader reader = metadataReaderFactory.getMetadataReader(resource);
            AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
            ClassMetadata classMetadata = reader.getClassMetadata();
            // 2.2.4 判断当前类为接口，并且标注了注解 ThirdPartyService，添加到结果集中
            if (classMetadata.isInterface() && annotationMetadata.isAnnotated(ThirdPartyService.class.getName())) {
                log.info("扫描到接口：{}", classMetadata.getClassName());
                result.add(reader);
            }
        }
        return result;
    }

    /**
     * 获取扫描的包路径
     *
     * @param attributes     {@link ThirdPartyServiceScan#value()}
     * @param defaultPackage 默认扫描的包路径，即 {@link ThirdPartyServiceScan} 注解标注类的所在包
     * @return 待扫描包路径集合
     */
    private List<String> getScanPackages(AnnotationAttributes attributes, String defaultPackage) {
        List<String> scanPackages = Arrays.stream(attributes.getStringArray("value"))
                .filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList());
        if (CollUtil.isEmpty(scanPackages)) {
            scanPackages.add(defaultPackage);
        }
        return scanPackages;
    }

    private static String convertPackageToResourcePath(String packageName) {
        return packageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
    }
}
