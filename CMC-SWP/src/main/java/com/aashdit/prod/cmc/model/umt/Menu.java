package com.aashdit.prod.cmc.model.umt;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.i18n.LocaleContextHolder;

import com.aashdit.prod.cmc.framework.core.annotation.Sortable;
import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="t_umt_menu", schema = "public")
public class Menu extends Auditable<User> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3929618635035564977L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id")
	private Long menuId;
			
	@Column(name="menu_en")
	@Sortable(lang="en")
	private String menuTextEN;
	
	@Column(name="menu_hi")
	@Sortable(lang="hi")
	private String menuTextHI;
	
	@Transient
	private String menuText;
	
	@Column(name="menu_icon")
	private String menuIcon;
	
	@Column(name="menu_uri")
	private String menuURL;
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name="parent_menu_id")
	@JsonIgnore
    private Menu parent;
		
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="parent")
    private List<Menu> children;
	
	@Column(name="display_order")
	private Integer displayOrder;
	
	@Column(name="is_active")
	private Boolean isActive;
	
	@Transient
	public Boolean isParent;
	
	@Column(name="is_display")
	private Boolean isDisplay;
	
	@Column(name="is_sys_config")
	private Boolean isSystemConfigEntry;
	
	@Column(name="is_module")
	private Boolean isModule;
	
	/* V 1.0.1 */
	
	@Column(name = "app_code")
	private String appCode;
	
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuTextEN() {
		return menuTextEN;
	}

	public void setMenuTextEN(String menuTextEN) {
		this.menuTextEN = menuTextEN;
	}

	public String getMenuTextHI() {
		return menuTextHI;
	}

	public void setMenuTextHI(String menuTextHI) {
		this.menuTextHI = menuTextHI;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuURL() {
		return menuURL;
	}

	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Boolean getIsSystemConfigEntry() {
		return isSystemConfigEntry;
	}

	public void setIsSystemConfigEntry(Boolean isSystemConfigEntry) {
		this.isSystemConfigEntry = isSystemConfigEntry;
	}

	public Boolean getIsModule() {
		return isModule;
	}

	public void setIsModule(Boolean isModule) {
		this.isModule = isModule;
	}


	public String getMenuText() {
		Locale locale = LocaleContextHolder.getLocale();
		switch (locale.getLanguage())
		{
			case "en":
				return this.getMenuTextEN();
			case "hi":
				return this.getMenuTextHI();
			default:
				return this.getMenuTextEN();
		}
	}

	public Boolean getIsParent() {
		//Any Menu that has children or is a module or is a submodule. Submodule URLs are always #
		return ((this.children != null && this.children.size() > 0) || this.isModule || this.menuURL.equals("#"));
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	
	

}
