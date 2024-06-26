package model;

import abstracts.AbstractObject;
import enums.EnumLogCategory;
import enums.EnumLogType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Log extends AbstractObject {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String ip;
	
	@Enumerated(EnumType.STRING)
	private EnumLogCategory category;
	
	@Enumerated(EnumType.STRING)
	private EnumLogType type;

	@Lob
	@Column
	private String stack;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public EnumLogCategory getCategory() {
		return category;
	}

	public void setCategory(EnumLogCategory category) {
		this.category = category;
	}

	public EnumLogType getType() {
		return type;
	}

	public void setType(EnumLogType type) {
		this.type = type;
	}
	
}
