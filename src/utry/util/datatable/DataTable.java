package utry.util.datatable;

import java.util.Date;
import java.util.List;
import java.util.Vector;

public class DataTable {
	
	public static boolean DEFAULT_READONLY = false;
	public static String DEFAULT_GETSTRING_NULL = "";
	public static int DEFAULT_GETINT_NULL = 0;
	public static Date DEFAULT_GETDATETIME_NULL = null;
	private DataColumnCollection columns;
	private DataRowCollection rows;
	private List<DataRow> deleteRows;
	private List<DataRow> entityRows;
	private int maxIndex=-1;
	private boolean readOnly=false;
	private String tableName;
	/**
	 * ���췽�������ɱ�
	 */
	public DataTable(){
		this.columns=new DataColumnCollection(this);
		this.rows=new DataRowCollection(this);
		this.entityRows=new Vector<DataRow>();
		this.readOnly=DEFAULT_READONLY;
		this.tableName="default";
	}
	public DataTable(String tableName){
		this.columns=new DataColumnCollection(this);
		this.rows=new DataRowCollection(this);
		this.entityRows=new Vector<DataRow>();
		this.readOnly=DEFAULT_READONLY;
		this.tableName=tableName;
	}
	
	//�����еĲ���
	/**
	 * ���һ�е������
	 * @param row
	 * @return
	 */
	public boolean addRow(DataRow row){
		return this.rows.addRow(row);
	}
	/**
	 * ������
	 * @param pos
	 * @param row
	 * @return
	 */
	public boolean insertRow(int pos, DataRow row){
		return this.rows.addRow(pos,row);
	}
	/**
	 * ɾ������е���
	 * @param pos
	 * @return
	 */
	public DataRow removeRow(int pos){
		DataRow dr=this.rows.remove(pos);
		/*
		if(dr!=null){
			this.getDeleteRows().add(dr);
		}
		*/
		return dr;
	}
	public boolean removeRow(DataRow row){
		boolean res=this.rows.remove(row);
		/*
		if(res){
			this.getDeleteRows().add(row);
		}
		*/
		return res;
	}
	/**
	 * �޸���
	 * @param pos
	 * @param row
	 * @return
	 */
	public int modifyRow(int pos, DataRow row){
		int res=-1;
		if(row.getTable()==this){
			this.getEntityRows().set(pos, row);
			res=1;
		}
		return res;
	}
	/**
	 * ��ѯ��
	 * @param pos
	 * @return
	 */
	public DataRow getRow(int pos){
		return this.entityRows.get(pos);
	}
	public List<DataRow> selectRow(String str){
		return this.rows.selectRow(str);
	}
	/**
	 * �ж��д���
	 * @param row
	 * @return
	 */
	public boolean rowIn(DataRow row){
		return this.entityRows.contains(row);
	}
	/**
	 * ������������ñ���е��е�����
	 * @param columnName
	 * @return
	 */
	public int getColumnIndex(String columnName){
		return this.columns.getNameMap().get(columnName);
	}
	/**
	 * ����һ������
	 * @return
	 */
	public DataRow newRow(){
		DataRow tempRow=new DataRow(this);
		return tempRow;
	}
	
	//�����еĲ���
	/**
	 * ���������
	 * @return
	 */
	public List<DataColumn> getColumns(){
		return this.columns.getColumns();
	}
	/**
	 * �����
	 * @param dc
	 * @return
	 */
	public int addColumn(DataColumn dc){
		return this.columns.add(dc);
	}
	public int addColumn(int pos, DataColumn dc){
		return this.columns.add(pos, dc);
	}
	public List<DataRow> getDeleteRows() {
		return deleteRows;
	}
	public void setDeleteRows(List<DataRow> deleteRows) {
		this.deleteRows = deleteRows;
	}
	public List<DataRow> getEntityRows() {
		return entityRows;
	}
	public void setEntityRows(List<DataRow> entityRows) {
		this.entityRows = entityRows;
	}
	public int getMaxIndex() {
		return maxIndex;
	}
	public void setMaxIndex(int maxIndex) {
		this.maxIndex = maxIndex;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}	
	
}
