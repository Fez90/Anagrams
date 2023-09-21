import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Feruz Karimov
 

public class Anagrams {
	// initializing constant primes to an array of 26 prime numbers
	final Integer[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
	
	Map<Character,Integer> letterTable;
	Map<Long,ArrayList<String>> anagramTable;
	
	public Anagrams() {
		buildLetterTable();
		anagramTable = new HashMap<Long,ArrayList<String>>();
	}
	
	private void buildLetterTable() {
		// building the hash table - letterTable
		// associate each letter of alphabet with prime numbers
		letterTable = new HashMap<Character,Integer>();
		char [] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		
		for(int i = 0; i < letters.length; i++) {
			letterTable.put(letters[i], primes[i]);
		}
		
	}
	
	private void addWord(String s) {
		// compute the hash code of the string s
		// after add the word to hash table anagramTable
		Long hash = myHashCode(s);
		
		if (anagramTable.get(hash) == null) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(s);
			anagramTable.put(hash, temp);
		}
		else {
			anagramTable.get(hash).add(s);
		}
	}
	
	private Long myHashCode(String s) {
		// Check if string is null, if true threw exception
		// given string s, compute its hash code
		if (s == null) {
			throw new IllegalArgumentException();
		}
		
		long result = 1;
		
		for(int i = 0; i < s.length(); i++) {
			result *= (long)letterTable.get(s.charAt(i));
		}
		
		return result;
	}
	
	private void processFile(String s) throws IOException {
		// The method receives the name of text file
		// Builds the hash table anagramTable, using method addWord
		FileInputStream fStream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fStream));
        String strLine;
	 
        while((strLine = br.readLine()) != null) {
			this.addWord(strLine);
		}
		br.close();
	}
	
	private ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries(){
		// method returns the entries in the anagram table that have the largest number of anagrams
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxList = new ArrayList<>(); 
		int max = 0;
		
		for (Map.Entry<Long,ArrayList<String>> entry : anagramTable.entrySet()) {
			
			int length = entry.getValue().size();
			
			  if(length > max) {
				  maxList.clear();
				  maxList.add(entry);
				  max = length;
				} 
			  else if(length == max) {
				  maxList.add(entry);
				}
			}
			 return maxList;	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Anagrams a = new Anagrams();
		
		final long startTime = System.nanoTime();
		
		try {
			a.processFile ("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long, ArrayList<String>>> maxEntries = a.getMaxEntries ();
		long key = maxEntries.get(0).getKey();
		int length = maxEntries.get(0).getValue().size();
		final long estimatedTime = System.nanoTime() - startTime ;
		final double seconds = ((double)estimatedTime/1000000000);
		System.out.println("Elapsed Time : "+ seconds);
		System.out.println("Key of maximum anagrams: "+ key);
		System.out.println("List of max anagrams: " + maxEntries.get(0).getValue());
		System.out.println("Length of list of max anagrams : "+ length);

	}

}
