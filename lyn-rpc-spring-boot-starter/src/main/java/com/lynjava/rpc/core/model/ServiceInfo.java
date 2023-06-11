package com.lynjava.rpc.core.model;

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
	 *  地址
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
}
