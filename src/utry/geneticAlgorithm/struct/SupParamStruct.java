package utry.geneticAlgorithm.struct;

public class SupParamStruct {

	//���ܸ���
	public int skillNum;
	//Ⱥ�����
	public int[] clusterNum;
	//�ֻ��������
	public int[][] alternateRuleNum;
	/*
	 * ���캯��
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
