package utry.geneticAlgorithm.struct;

import utry.util.datatable.DataTable;
/**
 * 轮换规则结构
 * @author DFF
 *
 */
public class AlternateRuleStruct {
	//轮换规则ID
	public String alternateId;
	//轮换规则表
	public DataTable alternateRuleList;
	public AlternateRuleStruct(String alternateId){
		this.alternateId=alternateId;
		this.alternateRuleList=new DataTable();
	}
}
