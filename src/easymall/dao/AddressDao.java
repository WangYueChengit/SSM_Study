package easymall.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import easymall.po.Address;
@Repository("addressDao")
@Mapper
public interface AddressDao {
	public void addAddress(Address address);
	public Address findAddress(Integer uid);
	public void updateAddress(Address address);
}
