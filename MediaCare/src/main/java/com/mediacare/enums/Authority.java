package com.mediacare.enums;

import java.util.HashMap;
import java.util.Map;

public enum Authority {

	PATIENT("e2b494f5-6738-41d2-8d9d-1e06e49c5574"),
	ADMIN("2b65497b-94c2-4582-8e24-207294832d29"),
	DOCTOR("618c5b30-842b-4e40-9820-6176c5accd41");
	
	private static Map<String, Authority> mapRole=new HashMap<String, Authority>(values().length,1);
	
	private final String uuid;
	
	private Authority(String uuid) {
		this.uuid=uuid;
	}
	
	static {
		for (Authority a : values()) {
			mapRole.put(a.uuid, a);
		}
	}
	
	public static Authority of(String uuid) {
		Authority authority = mapRole.get(uuid);
		if (authority==null) {
			return null;
		}
		return authority;
	}
	public String getUUID() {
		return uuid;
	}
	
}
