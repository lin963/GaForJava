package utry.util.datatable;

import java.util.LinkedHashMap;
import java.util.Map;

import utry.util.convert.DataTypes;

/**
 * 数据行
 * @author DFF
 *
 */
public class DataRow{
	
	//private DataColumnCollection columns;
	//用于存储数据的Map对象，这里保存的对象不包括顺序信息，数据获取的索引通过行信息标识
	private Map<String, Object> itemMap=new LinkedHashMap<String, Object>();
	//定义该行记录在table所处的行数
	private int rowIndex=-1;
	//table的一个引用
	private DataTable table;
	//构造方法
	public DataRow(){
	}
	public DataRow(DataTable table){
		this.table=table;
	}
	public DataRow(DataTable table, int rowIndex){
		this.table=table;
		this.rowIndex=rowIndex;
	}
	/**
	 * 根据列名获得列
	 * @param columnName
	 * @return
	 */
	private DataColumn getColumn(String columnName){
		return getColumn(this.table.getColumnIndex(columnName));
	}
	/**
	 * 根据列索引获得列
	 * @param index
	 * @return
	 */
	private DataColumn getColumn(int index){
		return getTable().getColumns().get(index);
	}
	/**
	 * 设置列的值，-1列不存在，1设置成功，2数据类型不同
	 * @param index
	 * @param ob
	 * @return
	 */
	public int setValue(int index, Object ob){
		int res=-1;
		DataColumn temp=getColumn(index);
		if(temp!=null){
			if(temp.getDataType()==DataTypes.getOjectDataType(ob)){
				this.itemMap.put(temp.getColumnName(), ob);
				res=1;
			}else{
				res=2;
			}
		}
		return res;
	}
	/**
	 * 设置列的值，-1列不存在，1设置成功，2数据类型不同
	 * @param columnName
	 * @param ob
	 * @return
	 */
	public int setValue(String columnName, Object ob){
		int res=-1;
		DataColumn temp=getColumn(columnName);
		if(temp!=null){
			if(temp.getDataType()==DataTypes.getOjectDataType(ob)){
				this.itemMap.put(temp.getColumnName(), ob);
				res=1;
			}else{
				res=2;
			}
		}
		return res;
	}
	/**
	 * 设置列的值，直接设置到列上，-1列不存在,1保存成功，2数据类型不相同，3
	 * @param column
	 * @param ob
	 * @return
	 */
	public int setValue(DataColumn column, Object ob){
		int res=-1;
		if(column!=null){
			if(table.getColumns().contains(column)){
				String lowerColumnName=column.getColumnName().toLowerCase().trim();
				if(getColumn(lowerColumnName).getDataType()==DataTypes.getOjectDataType(ob)){
					getItemMap().put(lowerColumnName, ob);
					res=1;
				}else{
					res=2;
				}
			}else{
				res=-1;
			}			
		}else{
			res=-1;
		}
		return res;
	}
	/**
	 * 根据索引位置去获得值，位置不存在则返回空
	 * @param index
	 * @return
	 */
	public Object getValue(int index){
		Object res=null;
		if(index>-1){
			String columnName=this.table.getColumns().get(index).getColumnName().toLowerCase().trim();
			res=getItemMap().get(columnName);
		}else{
			res=null;
		}
		return res;
	}
	/**
	 * 根据列名获取值，列名不存在或者输入为空，则都返回空，否则返回具体值
	 * @param columnName
	 * @return
	 */
	public Object getValue(String columnName){
		Object res=null;
		if(columnName!=null&&!columnName.equals("")){
			res=this.getItemMap().get(columnName.toLowerCase().trim());
		}
		return res;
	}
	/**
	 * 从当前表中复制行，行不是当前表中的行，返回空；是当前表中的行，则结果 复制
	 * @param row
	 * @return
	 */
	public boolean copyFrom(DataRow row){
		boolean res=false;
		if(row.table==this.table){			
			this.itemMap.clear();
			for(Object ob:this.table.getColumns()){
				this.itemMap.put(ob.toString().toLowerCase(), row.getValue(ob.toString().trim()));
			}
			res=true;
		}
		return res;
	}
	public Map<String, Object> getItemMap() {
		return itemMap;
	}
	public void setItemMap(Map<String, Object> itemMap) {
		this.itemMap = itemMap;
	}
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public DataTable getTable() {
		return table;
	}
	public void setTable(DataTable table) {
		this.table = table;
	}	
}
