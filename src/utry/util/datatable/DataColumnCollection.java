package utry.util.datatable;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * �����м���
 * @author DFF
 *
 */
public class DataColumnCollection {

	//������
	private List<DataColumn> columns;
	//��¼�б����λ��
	private HashMap<String, Integer > nameMap;
	private DataTable table;
	
	public DataColumnCollection(DataTable table){
		this.nameMap=new HashMap<String, Integer>(); 
		this.columns=new Vector<DataColumn>();
		this.table=table;
	}
	/**
	 * ������������
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
	 * �����������λ
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
	 * ����е�����
	 * @return
	 */
	public int size(){
		return this.columns.size();
	}
	/**
	 * �������
	 */
	public void clear(){
		this.columns.clear();
		this.nameMap.clear();
	}
	/**
	 * ��������λ�û��������
	 * @param index
	 * @return
	 */
	public DataColumn getDataColumn(int index){
		return this.columns.get(index);
	}
	/**
	 * �����������������
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
	 * ��������ӵķ���
	 * @param dc
	 * @return
	 * 1��ʾ��ӳɹ���2��ʾ������Ѵ��ڣ�3��ʾ�������д��ڣ����������(������ȣ����������Ͳ���),-1���ʧ��
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
	 * ����е������λ��
	 * @param index
	 * @param dc
	 * @return
	 * 1��ʾ��ӳɹ���2��ʾ������Ѵ��ڣ�3��ʾ�������д��ڣ����������(������ȣ����������Ͳ���),-1���ʧ��
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
	 * �����У��Ƴ��У�
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
	 * �����������Ƴ���
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
	 * ���������Ƴ���
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
