package rs.fon.jgrass.main;

import java.util.List;

import rs.fon.jgrass.domain.Member;
import rs.fon.jgrass.util.ParlamentApiCommunication;

public class Main {

	public static void main(String[] args) throws Exception {

		ParlamentApiCommunication comm = new ParlamentApiCommunication();

		List<Member> members = comm.getMembers();

		for (Member m : members) {
			System.out.println(m);
		}
	}

}
