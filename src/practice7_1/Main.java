package practice7_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

class Person {
	public int id;
	public String phone;

	public Person(int id, String phone) {
		this.phone = phone;
		this.id = id;
	}

	@Override
	public String toString() {
		return id + "(" + phone + ")";
	} // 객체 출력시 사용됨

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}

class PersonManager {
	// 문제 1
	// TreeMap을 생성한 후 list에 있는 모든 항목을 TreeMap에 삽입한 후 이 Map을 반환하라.
	// 이때 Map의 키로 Person의 id를 활용하라.
	// TreeMap은 HashMap과 생성 및 사용방법이 동일하다.
	// 다만, TreeMap은 키 순으로 원소를 관리하며 출력시 키순으로 출력된다.
	public Map<Integer, Person> makeMap(List<Person> list) {
		var t = new TreeMap<Integer, Person>();
		for (int i = 0; i < list.size(); i++) {
			t.put(list.get(i).id, list.get(i));
		}
		return t;
	}

	// 문제 2
	// 이 list의 원소들을 문자열의 [역순]으로 재배치하라.
	// list의 값으로 phoneList가 넘어 오는데 전화번호들이
	// 큰 전화번호에서 작은 전화번호 순으로 배치되어야 함
	public void sortReverse(List<String> list) {
		Collections.sort(list);
		Collections.reverse(list);
	}

	// 문제 3
	// treeMap의 모든 키와 값을 가지는 새로운 HashMap을 만든 후
	// 이 HashMap의 키들의 집합인 Set< Integer>을 반환한다.
	public Set<Integer> treeToHashKeySet(Map<Integer, Person> treeMap) {
		var h = new HashMap<Integer, Person>();
		for (var a : treeMap.keySet()) {
			h.put(a, treeMap.get(a));
		}
		return h.keySet(); // 구현 시 적절히 교체할 것
	}

	// 문제 4
	// 새로운 List< Person> foundList를 생성한 후 (ArrayList< >, Vector< >, LinkedList< > 중
	// 하나 택일)
	// 매개변수 phoneList에 저장된 각각의 전화번호(phone)에 대해
	// 이 phone 번호가 매개변수인 map에 내에 존재하는 전화번호일 경우
	// (map 내의 모든 Person 객체의 전화번호와 phone 번호를 일일히 비교해서 찾아야 함)
	// map 내의 해당 Person 객체를 foundList에 추가한다.
	// 마지막에 foundList을 반환함
	public List<Person> findPhones(Map<Integer, Person> map, List<String> phoneList) {
		var foundList = new Vector<Person>();
		var a = map.keySet();
		for (int i = 0; i < phoneList.size(); i++) {
			for (var p : a) {
				if (map.get(p).getPhone().equals(phoneList.get(i))) {
					foundList.add(map.get(p));
					break;
				}
			}
		}
		return foundList; // 구현 시 적절히 교체할 것
	}
}

public class Main {

	static List<String> phoneList;
	static List<Person> personList;
	static Map<Integer, Person> treeMap;

	// phoneList와 personList를 자동으로 생성한 후 리스트를 출력한다.
	public static void makePersonArrrayList(long seed) {
		Random rand = new Random(seed);
		phoneList = new ArrayList<String>();
		personList = new ArrayList<Person>();
		for (int i = 0; i < 4; ++i) {
			String phone = rand.nextInt(1000) + "-" + rand.nextInt(10000);
			phoneList.add(phone);
			personList.add(new Person(rand.nextInt(90) + 10, phone));
		}
		System.out.println("\nPhone  List: " + phoneList);
		System.out.println("Person List: " + personList); // 리스트의 모든 항목을 출력함
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("seed[0~9] for random? ");
		makePersonArrrayList(in.nextLong()); // Person 객체들의 리스트를 생성
		PersonManager pm = new PersonManager();

		while (true) {
			System.out.print("\n0.exit 1.makeMap 2.sortRreverse 3.treeToHashMap 4.findPhones >> ");
			int input = in.nextInt();
			if (input == 0)
				break;
			switch (input) {
			case 1:
				treeMap = pm.makeMap(personList);
				System.out.println("\nTree Map: " + treeMap);
				break;
			case 2:
				pm.sortReverse(phoneList);
				System.out.println("\nPhone List: " + phoneList);
				break;
			case 3:
				Set<Integer> keySet = pm.treeToHashKeySet(treeMap);
				System.out.println("\nHash Key Set: " + keySet);
				break;
			case 4:
				phoneList.remove(0);
				phoneList.remove(0);
				List<Person> pList = pm.findPhones(treeMap, phoneList);
				System.out.println("\nFound List: " + pList);
				break;
			}
		}
		System.out.print("\nGood bye! ");
		in.close();
	}
}