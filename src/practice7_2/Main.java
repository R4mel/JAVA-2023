package practice7_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

//문제 2: 제네릭 클래스로 변환하라.
class GVector<T> {
	private int size;
	private T[] arr;

	@SuppressWarnings("unchecked")
	public GVector() {
		size = 0;
		arr = (T[]) new Object[10];
	}

	public int size() {
		return size;
	}

	public T get(int idx) {
		return (T) arr[idx];
	}

	public void add(T value) {
		arr[size++] = (T) value;
	}

	public T set(int idx, T value) {
		T prev = (T) arr[idx];
		arr[idx] = value;
		return prev;
	}

	@Override
	public String toString() { // 코드를 수정하지 마라.
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (size > 0)
			sb.append(arr[0]);
		for (int i = 1; i < size; ++i)
			sb.append(", " + arr[i]);
		sb.append("]");
		return sb.toString();
	}
}

class GenericMethod {
	// 문제 2, 문제 3
	static public <K, V> TreeMap<K, V> toTreeMap(List<K> keyList, GVector<V> valueV) {
		TreeMap<K, V> map = new TreeMap<>();
		for (int i = 0; i < keyList.size(); ++i) {
			V value = valueV.get(i);
			map.put(keyList.get(i), value);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	static public <T> void swap(List<T> list, int i, int j) { // 문제 1
		Object tmp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, (T) tmp);
	}
}

public class Main {
	static Random rand;
	static List<Integer> idL;
	static List<String> phoneL;
	//	static GVector_Int idV; // 문제 2
	static GVector<String> phoneV; // 문제 2

	// idL와 phoneL를 자동 생성함
	public static void makeList() {
		idL = new ArrayList<Integer>();
		phoneL = new ArrayList<String>();
		for (int i = 0; i < 4; ++i) {
			idL.add(rand.nextInt(90) + 10);
			phoneL.add(rand.nextInt(1000) + "-" + rand.nextInt(10000));
		}
		System.out.println("ID    List: " + idL);
		System.out.println("Phone List: " + phoneL);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("seed[0~9] for random? ");
		rand = new Random(in.nextLong());
		System.out.println();
		makeList(); // idL와 phoneL를 자동 생성함
		final GVector<Integer> idV = new GVector<>(); // 문제 2
		final GVector<String> phoneV = new GVector<>(); // 문제 2

		while (true) {
			System.out.print("\n0.exit 1.swap 2.makeVector 3.makeMap >> ");
			int input = in.nextInt();
			System.out.println();
			if (input == 0)
				break;
			switch (input) {
				case 1:
					for (int i = 0; i < idL.size(); i++)
						GenericMethod.swap(idL, i, rand.nextInt(idL.size()));
					for (int i = 0; i < phoneL.size(); i++) // 문제 1
						GenericMethod.swap(phoneL, i, rand.nextInt(phoneL.size()));
					System.out.println("ID    List: " + idL);
					System.out.println("Phone List: " + phoneL);
					break;
				case 2:
					for (int i = 0; i < idL.size(); ++i) {
						idV.add(idL.get(i));
						phoneV.add(phoneL.get(i)); // 문제 2
					}
					System.out.println("ID    Vector: " + idV);
					System.out.println("Phone Vector: " + phoneV); // 문제 2
					int id = idV.get(0);
					for (int i = 0; i < 3; ++i)
						idV.set(i, idV.get(i + 1));
					idV.set(3, id);

					String phone = phoneV.get(0); // 문제 2
					for (int i = 0; i < 3; ++i) // 문제 2
						phoneV.set(i, phoneV.get(i + 1)); // 문제 2
					phoneV.set(3, phone); // 문제 2
					System.out.println();
					System.out.println("ID    Vector: " + idV);
					System.out.println("Phone Vector: " + phoneV); // 문제 2
					break;
				case 3:
					TreeMap<String, Integer> mapPhoneId = GenericMethod.toTreeMap(phoneL, idV);
					System.out.println("< Phone, ID> Map: " + mapPhoneId);
					TreeMap<Integer, String> mapIdPhone = GenericMethod.toTreeMap(idL, phoneV);
					// 문제 3
					System.out.println("< ID, Phone> Map: " + mapIdPhone); // 문제 3
					break;
			}
		}
		System.out.print("Good bye! ");
		in.close();
	}
}