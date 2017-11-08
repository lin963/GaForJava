package utry.util.datatable;
/**
 * ���ݱ��е���
 * @author DFF
 *
 */
public class DataColumn {
	//��������
	private String captionName;
	//������
	private int columnIndex;
	//������
	private String columnName;
	//�������ͣ�������ֵ��ʾ
	private int dataType;
	//������������
	private String dataTypeName;
	//ֻ������
	private boolean readOnly;
	//�����ڵı�����
	private DataTable table;
	
	//���췽��
	public DataColumn(){
		this("default1");
	}
	public DataColumn(String columnName){
		this(columnName,0);
	}
	public DataColumn(String columnName, int datatype){
		this.setDataType(datatype);
		this.columnName=columnName;
	}
	/**
	 * �ж��������Ƿ����
	 * @param dc
	 * @return
	 */
	public boolean equals(DataColumn dc){
		if(this.columnName.equals(dc.getColumnName())&&this.dataType==dc.dataType){
			return true;
		}else{
			return false;
		}
	}
	public String getCaptionName() {
		return captionName;
	}
	public void setCaptionName(String captionName) {
		this.captionName = captionName;
	}
	public int getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getDataTypeName() {
		return dataTypeName;
	}
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public DataTable getTable() {
		return table;
	}
	public void setTable(DataTable table) {
		this.table = table;
	}
	
}
