package utry.efficiencyFirst;

import java.util.ArrayList;
import java.util.List;

import utry.geneticAlgorithm.GaForJava;
import utry.util.convert.DataTypes;
import utry.util.datatable.DataColumn;
import utry.util.datatable.DataTable;
/**
 * ��϶������Ű��㷨
 * @author DFF
 *
 */
public class EffectFsted {
	//ָ������Ű൥λ
	static List<Integer> designSchUnit;
	//ÿ������ָ������Ű൥λ
	static List<List<Integer>> designUnitEachDaySkill;
	//ÿ������ÿ��ÿ�ְ�ε�ʹ�����,���һά��2λ,��һλ���:��α�ʾ1-N, ��2λ���:�ϰ൥λ����
	static List<List<List<Integer>>> banCiUseInforDaySkill;
	//��¼ÿ������ÿ����ÿ���ϰ൥λ����ϸ��Ϣ
	static List<List<List<List<RestUnitInf>>>> workUnitInfEachDayEachKeySkill;
	//��¼ÿ������ÿ����ÿ����Ϣ��λ����ϸ��Ϣ
	static List<List<List<List<RestUnitInf>>>> restUnitInfEachDayEachKeySkill;
	//��¼����ÿ��İ�ο�(Ⱥ���β���)
	static List<List<DataTable>> banCiLib;
	//��¼ÿ������ÿ��Ⱥ��滮����Ϣ����
	static List<List<Integer>> planRestDClusterSkill;
	//ÿ������ÿ��ÿ��ʱ��ָ�����Ѱ�������
	static float[][][] designSetManPowerEverySkill=new float[GaForJava.supParam.skillNum][][];
	//��¼ÿ������ÿ��ÿ����ζ�Ӧÿ��Ⱥ���µİ�κ�
	static List<List<List<List<List<Integer>>>>> banCiRation;
	//��¼ÿ������ÿ��ÿ����ζ�Ӧ�ü��ܰ�ϵ������,���һλ���:��ϵ����
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
