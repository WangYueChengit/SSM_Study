package easymall.service;

import easymall.po.Address;

public interface AddressService {
	public void addAddress(Address address);
	public Address findAddress(Integer uid);
	public void updateAddress(Address address);
}
