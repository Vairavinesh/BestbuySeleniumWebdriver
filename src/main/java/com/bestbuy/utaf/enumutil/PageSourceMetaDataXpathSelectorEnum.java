/**
 * 
 */
package com.bestbuy.utaf.enumutil;

import org.apache.commons.collections.functors.IfClosure;

import com.bestbuy.utaf.util.PageSourceConstants;

/**
 * @author BestBuy Inc.
 * @creator Thomas J. Schneider
 *
 */
public enum PageSourceMetaDataXpathSelectorEnum {
	APPLICATION(){
		public String getXpath(String pageId, String type, String parentId) {
			return PageSourceConstants.METADATA_APPLICATION_XPATH;
		};
	},
	PAGE(){
		public String getXpath(String pageId, String type, String parentId) {
			return PageSourceConstants.METADATA_PAGELIST_XPATH;
		};
	},
	TYPE() {
		public String getXpath(String pageId, String type, String parentId) {
			return PageSourceConstants.METADATA_TYPELIST_XPATH.replace("~pageIdVar~", pageId);
		};
	},
	ELEMENT() {
		public String getXpath(String pageId, String type, String parentId) {
			String xpathString = PageSourceConstants.METADATA_ELEMENTLIST_XPATH.replace("~pageIdVar~", pageId);
			return xpathString.replace("~typeVar~", type);
		};
	},
	ITEM() {
		public String getXpath(String pageId, String type, String parentId) {
			String xpathString = PageSourceConstants.METADATA_ITEM_XPATH.replace("~pageIdVar~", pageId);
			String parentStringXpath = PageSourceMetaDataXpathSelectorEnum.valueOf(parentId.toUpperCase()).getXpath(pageId, type, null);
			xpathString = xpathString.replace("~parentXpathVar~", parentStringXpath);
			return xpathString.replace("~typeVar~", type);
		};
	};
	public String getXpath(String pageId, String type, String parentId) {
		return null;
	}

}
