package com.da.b24tm;

public enum Operation {
	OPENED("open"), PAUSED("pause"), CLOSED("close"), STATUS("status"), AUTO("auto"), EXIT("exit");

	private final String value;

	Operation(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
