package utry.geneticAlgorithm.struct;

import utry.util.datatable.DataTable;
/**
 * �ֻ�����ṹ
 * @author DFF
 *
 */
public class AlternateRuleStruct {
	//�ֻ�����ID
	public String alternateId;
	//�ֻ������
	public DataTable alternateRuleList;
	public AlternateRuleStruct(String alternateId){
		this.alternateId=alternateId;
		this.alternateRuleList=new DataTable();
	}
}
