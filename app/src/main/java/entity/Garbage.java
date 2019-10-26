package entity;


public class Garbage {

	//垃圾的ID
	private String id ;
	//垃圾的名称
	private String name;
	//垃圾的类别
	private int category;
	//构造方法（无参数）
	public Garbage() {
		super();
		// TODO Auto-generated constructor stub
	}
	//构造方法（有参数）
	public Garbage(String name, int category) {
		super();
		this.name = name;
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
	
	
}
