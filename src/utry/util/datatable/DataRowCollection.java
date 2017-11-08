package utry.util.datatable;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据列的集合
 * @author DFF
 *
 */
public class DataRowCollection {
	
	private DataTable table;

	public DataRowCollection(DataTable table){
		this.table=table;
	}
	/**
	 * 返回存储大小
	 * @return
	 */
	public int size(){
		return this.table.getEntityRows().size();
	}
	/**
	 * 根据查找条件查找行
	 * @param str
	 * @return
	 */
	public List<DataRow> selectRow(String str){
		List<DataRow> rows=new ArrayList<DataRow>();
		String[] strs=str.replace("'", "").split("=");
		for(DataRow tempRow:this.table.getEntityRows()){
			if(tempRow.getValue(strs[0]).equals(strs[1])){
				rows.add(tempRow);
			}
		}		
		return rows;
	}
	/**
	 * 清除所有的行
	 */
	public void clear(){
		this.table.getEntityRows().clear();
	}
	/**
	 * 设置行的位置
	 * @param pos
	 */
	private void changeRowIndex(int pos){
		int count=this.table.getEntityRows().size();
		for(int i=count;i>=pos;i--){
			this.table.getEntityRows().get(i).setRowIndex(i);
		}
	}
	/**
	 * 添加一行到具体的位置
	 * @param index
	 * @param row
	 * @return
	 */
	public boolean addRow(int index,DataRow row){
		boolean res=false;
		if(row!=null&&row.getTable().equals(this.table)){
			this.table.getEntityRows().add(index, row);
			res=true;
			changeRowIndex(index);
		}
		return res;
	}
	/**
	 * 添加一行到末尾位置
	 * @param row
	 * @return
	 */
	public boolean addRow(DataRow row){
		boolean res=false;
		if(row!=null&&row.getTable().equals(this.table)){
			//指定row的位置
			row.setRowIndex(this.table.getEntityRows().size());
			this.table.getEntityRows().add(row);
			res=true;
		}
		return res;
	}
	/**
	 * 添加一新行到末尾
	 * @return
	 * @throws Exception
	 */
	public DataRow addNewRow() throws Exception{
		DataRow tempRow=this.table.newRow();
		if(tempRow!=null){
			if(!addRow(tempRow)){
				tempRow=null;
			}
		};
		return tempRow;
	}
	/**
	 * 添加新行到指定位置
	 * @param pos
	 * @return
	 * @throws Exception
	 */
	public DataRow addNewRow(int pos) throws Exception{
		DataRow tempRow=this.table.newRow();
		if(tempRow!=null){
			if(!addRow(pos,tempRow)){
				tempRow=null;
			}
		};
		return tempRow;
	}
	/**
	 * 根据行的索引获得行
	 * @param index
	 * @return
	 */
	public DataRow getRow(int index){
		return this.getTable().getEntityRows().get(index);
	}
	/**
	 * 删除行的方法
	 * @param row
	 * @return
	 */
	public boolean remove(DataRow row){
		return this.table.getEntityRows().remove(row);
	}
	public DataRow remove(int pos){
		DataRow row=getRow(pos);
		this.getTable().getEntityRows().remove(pos);
		return row;		
	}
	public DataTable getTable() {
		return table;
	}
	public void setTable(DataTable table) {
		this.table = table;
	}
	
	
}
