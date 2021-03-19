package easymall.po;

public class Address {
	private Integer uid;
	private String address;
	public Address(Integer uid, String address) {
		this.uid = uid;
		this.address = address;
	}
	public Address() {
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
