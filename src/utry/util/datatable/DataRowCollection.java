package utry.util.datatable;

import java.util.ArrayList;
import java.util.List;

/**
 * �����еļ���
 * @author DFF
 *
 */
public class DataRowCollection {
	
	private DataTable table;

	public DataRowCollection(DataTable table){
		this.table=table;
	}
	/**
	 * ���ش洢��С
	 * @return
	 */
	public int size(){
		return this.table.getEntityRows().size();
	}
	/**
	 * ���ݲ�������������
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
	 * ������е���
	 */
	public void clear(){
		this.table.getEntityRows().clear();
	}
	/**
	 * �����е�λ��
	 * @param pos
	 */
	private void changeRowIndex(int pos){
		int count=this.table.getEntityRows().size();
		for(int i=count;i>=pos;i--){
			this.table.getEntityRows().get(i).setRowIndex(i);
		}
	}
	/**
	 * ���һ�е������λ��
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
	 * ���һ�е�ĩβλ��
	 * @param row
	 * @return
	 */
	public boolean addRow(DataRow row){
		boolean res=false;
		if(row!=null&&row.getTable().equals(this.table)){
			//ָ��row��λ��
			row.setRowIndex(this.table.getEntityRows().size());
			this.table.getEntityRows().add(row);
			res=true;
		}
		return res;
	}
	/**
	 * ���һ���е�ĩβ
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
	 * ������е�ָ��λ��
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
	 * �����е����������
	 * @param index
	 * @return
	 */
	public DataRow getRow(int index){
		return this.getTable().getEntityRows().get(index);
	}
	/**
	 * ɾ���еķ���
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
