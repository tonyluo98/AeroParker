
public class CustomerSites {
	private int customerId , siteId;
	
	public CustomerSites(int customerId, int siteId) {
		super();
		this.customerId = customerId;
		this.siteId = siteId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}



}
