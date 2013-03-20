package net.sinoace.library.utils;

/**
 * 这是一个圆圈
 * @param <T>
 */
public class Circle<T> {
	/**
	 * 最大容量
	 */
	private int maxSize;
	/**
	 * 头节点
	 */
	private Node headerNode;
	/**
	 * 尾节点
	 */
	private Node footerNode;
	/**
	 * 容量
	 */
	private int size;
	
	/**
	 * 创建一个圆圈，同时你必须指定它的最大容量
	 * @param maxSize 最大容量
	 */
	public Circle(int maxSize){
		setMaxSize(maxSize);
	}
	
	/**
	 * 放入一个对象
	 * @param object
	 */
	public void put(T object){
		//如果还没有满
		if(size < maxSize){
			//如果头节点还是空的，说明当前是空的
			if(headerNode == null){
				headerNode = new Node(object);
				footerNode = headerNode;
			}else{
				Node endNode = new Node(object);
				footerNode.setNext(endNode);
				footerNode = endNode;
			}
			size++;
		}else{
			Node endNode = new Node(object);
			footerNode.setNext(endNode);
			footerNode = endNode;
			headerNode = headerNode.getNext();
		}
	}
	
	/**
	 * 删除一个对象
	 * @return 被删除的对象
	 */
	@SuppressWarnings("unchecked")
	public T remove(){
		T object = null;
		if(headerNode != null){
			object = (T) headerNode.getObject();
			headerNode = headerNode.getNext();
			size--;
		}
		return object;
	}
	
	/**
	 * 清空
	 */
	public void clear(){
		headerNode = null;
		footerNode = null;
		size = 0;
	}
	
	/**
	 * 当前大小
	 * @return 当前大小
	 */
	public int size(){
		return size;
	}

	/**
	 * 获取最大容量
	 * @return 最大容量
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * 设置最大容量
	 * @param maxSize 最大容量
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}