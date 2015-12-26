package hu.tvarga.recyclerviewtest.model;

import java.io.Serializable;

public class POJOForList implements Serializable {
	private static final long serialVersionUID = 1L;
	private String label;
	private Integer value;

	public String getLabel() {
		return label;
	}

	public Integer getValue() {
		return value;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "POJOForList [label=" + label + ", value=" + value + "]";
	}

}