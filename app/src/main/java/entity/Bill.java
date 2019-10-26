package entity;
//订单类

import java.util.Date;

public class Bill {

	private String id;
	//订单用户的个人信息
	private User User;
	//订单回收员的信息
	private Collector collector;
	//订单现在的状态
	private String state;
	//订单的价格
	private String price;
	//订单的下单时间
	private Date date;
	//订单最后回收到那个回收站总部
	private String headquarters;
	//订单是否被总部收取
	private Boolean Whether_recovery;
	//订单是否被下单处理，并返款给客户
	private Boolean returnMonenyforcustomer;
	//订单返回给客户的钱
	private Double price_forcustomer;
	
	
	//构造方法（无参数）
	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bill(String id,String price){

		super();
		this.id=id;
		this.price=price;
	}

	//构造方法（有参数）
	public Bill(String id, User user, Collector collector, String state, String price, Date date,
			String headquarters, Boolean whether_recovery, Boolean returnMonenyforcustomer, Double price_forcustomer) {
		super();
		this.id = id;
		User = user;
		this.collector = collector;
		this.state = state;
		this.price = price;
		this.date = date;
		this.headquarters = headquarters;
		Whether_recovery = whether_recovery;
		this.returnMonenyforcustomer = returnMonenyforcustomer;
		this.price_forcustomer = price_forcustomer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public Collector getCollector() {
		return collector;
	}

	public void setCollector(Collector collector) {
		this.collector = collector;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHeadquarters() {
		return headquarters;
	}

	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}

	public Boolean getWhether_recovery() {
		return Whether_recovery;
	}

	public void setWhether_recovery(Boolean whether_recovery) {
		Whether_recovery = whether_recovery;
	}

	public Boolean getReturnMonenyforcustomer() {
		return returnMonenyforcustomer;
	}

	public void setReturnMonenyforcustomer(Boolean returnMonenyforcustomer) {
		this.returnMonenyforcustomer = returnMonenyforcustomer;
	}

	public Double getPrice_forcustomer() {
		return price_forcustomer;
	}

	public void setPrice_forcustomer(Double price_forcustomer) {
		this.price_forcustomer = price_forcustomer;
	}
	
	
	
	
}
