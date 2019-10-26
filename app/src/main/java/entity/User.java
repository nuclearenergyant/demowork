package entity;


//用户信息
public class User {

	//用户的ID
	private int id;
	//用户的手机号码
	private String phone;
	//用户的密码
	private String password;
	//用户的邮箱
	private String email;
	//用户的地址
	private String address;
	//用户的订单
	private Bill bill;
	
	//构造方法（无参数）
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	//构造方法（有参数）

	public User(String phone, String password) {
		super();
		this.phone = phone;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	
	
}
