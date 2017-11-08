package utry.util.convert;
/**
 * 数据类类型的整数表达式
 * @author DFF
 *
 */
public class DataTypes {
	
	public static final int DATATABLEOBJECT = 0;
	public static final int DATATABLESTRING = 1;
	public static final int DATATABLEBOOLEAN = 2;
	public static final int DATATABLESHORT = 3;
	public static final int DATATABLEINT = 4;
	public static final int DATATABLELONG = 5;
	public static final int DATATABLEFLOAT = 6;
	public static final int DATATABLEDOUBLE = 7;
	public static final int DATATABLEDATE = 8;
	public static final int DATATABLETIME = 9;
	public static final int DATATABLETIMESTAMP = 10;
	public static final int DATATABLEBYTE = 11;
	public static final int DATATABLEBYTES = 12;
	public static final int DATATABLEBIGDECIMAL = 13;
	//数据类型的名称
	public static final String[] DATATABLETYPENAMES = { "OBJECT", 
			"STRING", "BOOLEAN", "SHORT", "INTEGER", "LONG", "FLOAT", "DOUBLE", 
			"DATE", "TIME", "TIMESTAMPLE", "BYTE", "BYTES", "BIGDECIMAL" };
	 //检查数据类型的存在性，用整型数字
	public static boolean checkDataType(int dataType){
	    return (dataType > -1) && (dataType < 14);
	}	  
	//获得数据类型的字型串
	public static String getDataTypeName(int dataType){
		if (checkDataType(dataType)) {
			return DATATABLETYPENAMES[dataType];
		}
			return null;
	}
	/**
	 * 根据数据类型名，获得数据类型的数字型式
	 * @param dataTypeName
	 * @return
	 */
	public static int getDataType(String dataTypeName){
		int type=-1;
		for(int i=0;i<DATATABLETYPENAMES.length;i++){
			if(DATATABLETYPENAMES[i].equals(dataTypeName.toUpperCase())){
				type=i;
				break;
			}
		}
		return type;
	}
	/**
	 * 获得数据对象的类型的数字表达形式，-1表示超出数据类型
	 * @param ob
	 * @return
	 */
	public static int getOjectDataType(Object ob){
		String obStr=ob.getClass().getName();
		String typeName=obStr.substring(obStr.lastIndexOf(".")+1).trim().toUpperCase();
		return getDataType(typeName);
		
	}
}
