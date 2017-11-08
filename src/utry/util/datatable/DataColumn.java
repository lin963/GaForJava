package utry.util.datatable;
/**
 * 数据表中的列
 * @author DFF
 *
 */
public class DataColumn {
	//标题名称
	private String captionName;
	//列索引
	private int columnIndex;
	//列名称
	private String columnName;
	//数据类型，用索引值表示
	private int dataType;
	//数据类型名称
	private String dataTypeName;
	//只读属性
	private boolean readOnly;
	//列属于的表名称
	private DataTable table;
	
	//构造方法
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
	 * 判断两个列是否相等
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
