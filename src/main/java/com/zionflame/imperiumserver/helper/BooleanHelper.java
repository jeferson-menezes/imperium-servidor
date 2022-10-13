package com.zionflame.imperiumserver.helper;

public interface BooleanHelper {

	default Boolean canNotNull(Boolean value) {
		return value == null ? Boolean.FALSE : value;
	}
}
