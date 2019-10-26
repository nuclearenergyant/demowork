package entity;
//配送员的信息
public class Collector extends User{
	//回收员ID
	private int id;
	//回收员是否通过考试
	private Boolean Pass_Exam;
	//回收员的订单数
	private int Order_number;
	//回收员的固定网点
	private String headquarters;
	//构造方法（无参数）
	public Collector() {
		super();
		// TODO Auto-generated constructor stub
	}
	//构造方法（有参数）
	public Collector(int id, Boolean pass_Exam, int order_number, String headquarters) {
		super();
		this.id = id;
		Pass_Exam = pass_Exam;
		Order_number = order_number;
		this.headquarters = headquarters;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getPass_Exam() {
		return Pass_Exam;
	}
	public void setPass_Exam(Boolean pass_Exam) {
		Pass_Exam = pass_Exam;
	}
	public int getOrder_number() {
		return Order_number;
	}
	public void setOrder_number(int order_number) {
		Order_number = order_number;
	}
	public String getHeadquarters() {
		return headquarters;
	}
	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}
	
}
