package utry.geneticAlgorithm.struct;

import java.util.List;

import utry.util.datatable.DataTable;

public class ClusterParamStruct {
	public String clusterId;
	public int scheUnit;
	public boolean whOneDaysOff;
	public boolean whTwoDaysOff;
	public int sameTimeAtOffWork;
	public boolean whPreInstallClass;
	public boolean whPreInstallPeople;
	public boolean whPreInstallSubgroup;
	public boolean whPreInstallUnit;
	public boolean whDesignClass;
	public boolean whDesignRest;
	public boolean whMaxMinContinueWorkRange;
	public boolean whSanXiDate;
	public boolean preInstallRand;
	public boolean preInstallBeforeRest;
	public boolean preInstallWorkday;
	public int designPeopleNum;
	public int lastPeroidWorkListEveryoneDaysNum;
	public DataTable scheUnitList;
	public DataTable workSeries;
	public DataTable workSort;
	public AlternateRuleStruct[] alternateRule;
	public DataTable workTaskList;
	public ConfortParamStruct conforParam;
	public DataTable lastPeriodWorkList;
	public int lastPeriodWorkListDaysNum;
	public int[][] lastClass;
	public DataTable designWorkList;
	public DataTable hrmStaffList;
	public DataTable preInstallWorkSeries;
	public DataTable preInstallWorkList;
	public DataTable lastPeriodWorkListEveryone;
	public DataTable individualNeeds;
	public DataTable scheduleWorkday;
	public DataTable sanDaySn;
	public boolean whFixedPaixiu;
	public boolean whFixBanci;
	public List<String> fixdBanci;
	public DataTable designStaffList;
	public ClusterParamStruct(int alternateRuleNum){
		clusterId="";
		scheUnit=-1;
		whOneDaysOff=false;
		whTwoDaysOff=false;
		
	}
	
	
	
	
	
}
