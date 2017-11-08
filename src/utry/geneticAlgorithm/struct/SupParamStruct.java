package utry.geneticAlgorithm.struct;

public class SupParamStruct {

	//技能个数
	public int skillNum;
	//群组个数
	public int[] clusterNum;
	//轮换规则个数
	public int[][] alternateRuleNum;
	/*
	 * 构造函数
	 */
	public SupParamStruct(int skillNum, int[] clusterNum){
		this.skillNum=skillNum;
		this.clusterNum=clusterNum;
		this.alternateRuleNum=new int[skillNum][];
		for(int i=0;i<skillNum;i++){
			this.clusterNum[i]=clusterNum[i];
			this.alternateRuleNum[i]=new int[clusterNum[i]];
		}
	}
}
