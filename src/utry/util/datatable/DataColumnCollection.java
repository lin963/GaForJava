package utry.util.datatable;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * 数据列集合
 * @author DFF
 *
 */
public class DataColumnCollection {

	//保存列
	private List<DataColumn> columns;
	//记录列保存的位置
	private HashMap<String, Integer > nameMap;
	private DataTable table;
	
	public DataColumnCollection(DataTable table){
		this.nameMap=new HashMap<String, Integer>(); 
		this.columns=new Vector<DataColumn>();
		this.table=table;
	}
	/**
	 * 更换所有列名
	 */

	private void doChangeNameMap(){
		this.nameMap.clear();
		int j=this.columns.size();
		for(int i=0;i<j;i++){
			DataColumn tempColumn=this.columns.get(i);
			this.nameMap.put(tempColumn.getColumnName().toLowerCase().trim().toString(),new Integer(i));
			this.columns.get(i).setColumnIndex(i);
		}
	}
	/**
	 * 获得列名索引位
	 * @param columnName
	 * @return
	 */
	private int getColumnIndex(String columnName){
		if(this.nameMap.containsKey(columnName.toLowerCase().trim())){
			return this.nameMap.get(columnName.toLowerCase().trim());
		}
		return -1;
	}
	/**
	 * 获得列的数量
	 * @return
	 */
	public int size(){
		return this.columns.size();
	}
	/**
	 * 清空列名
	 */
	public void clear(){
		this.columns.clear();
		this.nameMap.clear();
	}
	/**
	 * 根据索引位置获得数据列
	 * @param index
	 * @return
	 */
	public DataColumn getDataColumn(int index){
		return this.columns.get(index);
	}
	/**
	 * 根据列名获得数据列
	 * @param columnName
	 * @return
	 */
	public DataColumn getDataColumn(String columnName){
		int index=getColumnIndex(columnName.toLowerCase().trim());
		if(index>-1){
			return getDataColumn(index);
		}
		return null;
	}
	
	/**
	 * 数据列添加的方法
	 * @param dc
	 * @return
	 * 1表示添加成功，2表示这个列已存在，3表示此数据列存在，但不能添加(列名相等，但数据类型不对),-1添加失败
	 */
	public int add(DataColumn dc){
		int isAdd=-1;
		int index=getColumnIndex(dc.getColumnName().toLowerCase().trim());
		if(index==-1){
			dc.setColumnIndex(columns.size());
			this.columns.add(dc);
			isAdd=1;
			doChangeNameMap();
		}else {
			if(this.columns.get(index).equals(dc)){
				isAdd=2;
			}else{
				isAdd=3;
			}
		}
		return isAdd;
	}
	/**
	 * 添加列到具体的位置
	 * @param index
	 * @param dc
	 * @return
	 * 1表示添加成功，2表示这个列已存在，3表示此数据列存在，但不能添加(列名相等，但数据类型不对),-1添加失败
	 */
	public int add(int pos, DataColumn dc){
		int isAdd=-1;
		int index=getColumnIndex(dc.getCaptionName().toLowerCase().trim());
		if(index==-1){
			//dc.setColumnIndex(index);
			this.columns.add(pos, dc);
			this.doChangeNameMap();
			isAdd=1;
		}else {
			if(this.columns.get(index).equals(dc)){
				isAdd=2;
			}else{
				isAdd=3;
			}
		}
		return isAdd;
	}
	/**
	 * 根据列，移除列，
	 * @param dc
	 * @return
	 */
	public boolean remove(DataColumn dc){
		boolean res=false;
		if(nameMap.containsKey(dc.getColumnName().toLowerCase().trim())){
			this.columns.remove(dc);
			res=true;
			doChangeNameMap();
		}
		return res;
	}
	/**
	 * 根据索引号移除列
	 * @param index
	 * @return
	 */
	public DataColumn remove(int index){
		DataColumn res=this.columns.remove(index);
		if(res!=null){
			this.nameMap.remove(res);
			doChangeNameMap();
		}		
		return res;
		
	}
	/**
	 * 根据列名移除列
	 * @param columnName
	 * @return
	 */
	public DataColumn remove(String columnName){
		int tempIndex=getColumnIndex(columnName);
		if(tempIndex>-1){
			doChangeNameMap();
			return remove(tempIndex);
		}else
			return null;
	}
	public List<DataColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<DataColumn> columns) {
		this.columns = columns;
	}
	public HashMap<String, Integer> getNameMap() {
		return nameMap;
	}
	public void setNameMap(HashMap<String, Integer> nameMap) {
		this.nameMap = nameMap;
	}
	public DataTable getTable() {
		return table;
	}
	public void setTable(DataTable table) {
		this.table = table;
	}
	
}
