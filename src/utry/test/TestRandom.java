package utry.test;

import java.util.Random;
import java.util.UUID;

import utry.util.datatable.DataRow;

public class TestRandom {

	private static int randbit(int start, int end, int p){
		int ret;
		Random random=new Random(UUID.randomUUID().hashCode()+p);
		ret=start+random.nextInt(end);
		return ret;
	}
	public static DataRow selectRow(String str){
		DataRow row=null;
		String[] strs=str.replace("'", "").split("=");
		for(int i=0;i<strs.length;i++){
			System.out.println(strs[i]);
		}
		return row;
	}
	public static void main(String[] args) {
		Random rand1=new Random();
		Random rand2=new Random();
		System.out.println(rand1.nextInt(100));
		System.out.println(rand2.nextInt(100));
	}

}
