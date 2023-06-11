package com.lynjava.rpc.core.model;

import com.lynjava.rpc.core.util.RpcUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceInfo implements Serializable {

	/**
     *  应用名称
	 */
	private String appName;

	/**
	 *  主机/IP
	 */
	private String address;

	/**
	 *  端口
	 */
	private Integer port;

	/**
	 * 服务名称
	 */
	private String serviceName;
	/**
	 *  版本
	 */
	private String version;

	/**
	 * 环境标识
	 */
	private String usf;

	private Integer weight;

	public String buildServiceKey() {
		return RpcUtils.serviceKey(appName, serviceName, version, usf);
	}
}
