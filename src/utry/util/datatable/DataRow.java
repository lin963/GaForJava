package utry.util.datatable;

import java.util.LinkedHashMap;
import java.util.Map;

import utry.util.convert.DataTypes;

/**
 * ������
 * @author DFF
 *
 */
public class DataRow{
	
	//private DataColumnCollection columns;
	//���ڴ洢���ݵ�Map�������ﱣ��Ķ��󲻰���˳����Ϣ�����ݻ�ȡ������ͨ������Ϣ��ʶ
	private Map<String, Object> itemMap=new LinkedHashMap<String, Object>();
	//������м�¼��table����������
	private int rowIndex=-1;
	//table��һ������
	private DataTable table;
	//���췽��
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
	 * �������������
	 * @param columnName
	 * @return
	 */
	private DataColumn getColumn(String columnName){
		return getColumn(this.table.getColumnIndex(columnName));
	}
	/**
	 * ���������������
	 * @param index
	 * @return
	 */
	private DataColumn getColumn(int index){
		return getTable().getColumns().get(index);
	}
	/**
	 * �����е�ֵ��-1�в����ڣ�1���óɹ���2�������Ͳ�ͬ
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
	 * �����е�ֵ��-1�в����ڣ�1���óɹ���2�������Ͳ�ͬ
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
	 * �����е�ֵ��ֱ�����õ����ϣ�-1�в�����,1����ɹ���2�������Ͳ���ͬ��3
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
	 * ��������λ��ȥ���ֵ��λ�ò������򷵻ؿ�
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
	 * ����������ȡֵ�����������ڻ�������Ϊ�գ��򶼷��ؿգ����򷵻ؾ���ֵ
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
	 * �ӵ�ǰ���и����У��в��ǵ�ǰ���е��У����ؿգ��ǵ�ǰ���е��У����� ����
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
