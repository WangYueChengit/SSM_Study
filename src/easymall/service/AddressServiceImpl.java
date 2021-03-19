package easymall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import easymall.dao.AddressDao;
import easymall.po.Address;
@Service("addressService")
public class AddressServiceImpl implements AddressService{
	@Autowired
	private AddressDao addressDao;
	@Override
	public void addAddress(Address address) {
		addressDao.addAddress(address);
	}

	@Override
	public Address findAddress(Integer uid) {
		return addressDao.findAddress(uid);
	}

	@Override
	public void updateAddress(Address address) {
		// TODO Auto-generated method stub
		addressDao.updateAddress(address);
	}

}
