package utry.efficiencyFirst;

import java.util.ArrayList;
import java.util.List;

import utry.geneticAlgorithm.GaForJava;
import utry.util.convert.DataTypes;
import utry.util.datatable.DataColumn;
import utry.util.datatable.DataTable;
/**
 * 拟合度优先排班算法
 * @author DFF
 *
 */
public class EffectFsted {
	//指定班的排班单位
	static List<Integer> designSchUnit;
	//每个技能指定班的排班单位
	static List<List<Integer>> designUnitEachDaySkill;
	//每个技能每天每种班次的使用情况,最后一维共2位,第一位存放:班次标示1-N, 第2位存放:上班单位数量
	static List<List<List<Integer>>> banCiUseInforDaySkill;
	//记录每个技能每个解每天上班单位的详细信息
	static List<List<List<List<RestUnitInf>>>> workUnitInfEachDayEachKeySkill;
	//记录每个技能每个解每天休息单位的详细信息
	static List<List<List<List<RestUnitInf>>>> restUnitInfEachDayEachKeySkill;
	//记录技能每天的班次库(群组班次并库)
	static List<List<DataTable>> banCiLib;
	//记录每个技能每个群组规划的休息天数
	static List<List<Integer>> planRestDClusterSkill;
	//每个技能每天每个时候指定班已安排人力
	static float[][][] designSetManPowerEverySkill=new float[GaForJava.supParam.skillNum][][];
	//记录每个技能每天每个班次对应每个群组下的班次号
	static List<List<List<List<List<Integer>>>>> banCiRation;
	//记录每个技能每天每个班次对应该技能班系索引号,最后一位存放:班系个数
	static List<List<List<Integer>>> banCiSkillRation;
	
	public void efficiencyFirst(){
		banCiLib=new ArrayList<List<DataTable>>();
		for(int m=0;m<GaForJava.supParam.skillNum;m++){
			banCiLib.add(new ArrayList<DataTable>());
			getBanCiLibSkill(m);
		}
	}
	public void getBanCiLibSkill(int m){
		List<String> listWorkDesc=new ArrayList<String>();
		int count=0;
		for(int j=0;j<GaForJava.pubParam.dmonth;j++){
			DataTable banCiLibrary=new DataTable();
			banCiLibrary.getColumns().add(new DataColumn("WorkDesc",DataTypes.getDataType("String")));
			banCiLibrary.getColumns().add(new DataColumn("IsTiaoBan",DataTypes.getDataType("String")));
			banCiLibrary.getColumns().add(new DataColumn("SetPerson",DataTypes.getDataType("String")));
			banCiLibrary.getColumns().add(new DataColumn("BanCiId",DataTypes.getDataType("String")));
			banCiLibrary.getColumns().add(new DataColumn("BanXiId",DataTypes.getDataType("String")));
			banCiLibrary.getColumns().add(new DataColumn("Time",DataTypes.getDataType("String")));
			listWorkDesc.clear();
			count=0;
			for(int n=0;n<GaForJava.supParam.clusterNum[m];n++){
				if(GaforJava.)
			}
		}
	}
	
}
