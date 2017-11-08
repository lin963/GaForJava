package utry.geneticAlgorithm.struct;

import utry.util.datatable.DataTable;

public class CsParamStruct {
	public String skillId;
	public DataTable teleTrafficePerc;
	public DataTable numPeopleAtleastEveryDay;
	public ClusterParamStruct[] clusterParam;
	public CsParamStruct(int clusterNum, int[] alternateRuleNum){
		skillId="";
		teleTrafficePerc=new DataTable();
		numPeopleAtleastEveryDay=new DataTable();
		clusterParam=new ClusterParamStruct[clusterNum];
	}
	
}
