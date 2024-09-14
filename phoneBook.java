import java.util.Scanner;

class PhoneBookEntry {
    String name;
    String number;

    public PhoneBookEntry(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public static String discard_name(String name) {
        String cleanedName = "";
        boolean capitalizeNext = true;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isLetter(c)) {
                if (capitalizeNext) {
                    cleanedName += Character.toUpperCase(c);
                    capitalizeNext = false;
                } else {
                    cleanedName += Character.toLowerCase(c);
                }
            } else if (c == ' ') {
                if (cleanedName.isEmpty()) {
                    continue;
                }
                if (cleanedName.charAt(cleanedName.length() - 1) != ' ') {
                    cleanedName += c;
                    capitalizeNext = true;
                }
            }
        }
    
        return cleanedName;
    }
    
    public static String cleanPhoneNumber(String phoneNumber) {
        if(phoneNumber.charAt(0)!='+'){
            return "+880" + phoneNumber;
        }
        else{
            return phoneNumber;
        }
    }

    public static boolean isMatch(String str, String searchStr) {
        int strLength = str.length();
        int searchLength = searchStr.length();
        
        for (int i = 0; i <= strLength - searchLength; i++) {
            int j;
            for (j = 0; j < searchLength; j++) {
                char c1 = Character.toLowerCase(str.charAt(i + j));
                char c2 = Character.toLowerCase(searchStr.charAt(j));
                if (c1 != c2) {
                    break;
                }
            }
            if (j == searchLength) {
                return true;
            }
        }
        return false;
    }
    public static int compareStrings(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int minLength = converter.min(len1, len2);
        
        for (int i = 0; i < minLength; i++) {
            char c1 = Character.toLowerCase(str1.charAt(i));
            char c2 = Character.toLowerCase(str2.charAt(i));
            
            if (c1 != c2) {
                return c1 - c2;
            }
        }
        
        return len1 - len2;
    }

    public static String formatNumber(String s){
        char arr[] = new char[s.length()];
        for(int i=0; i<s.length(); i++){
            arr[i] = s.charAt(i);
        }
        String str = "";
        
           for(int i=0;i<=3;i++){
               str += arr[i];
           }
              str += " ";
            for(int i=4;i<=8;i++){
               str += arr[i];
        }
        str += "-";
        for(int i=9;i<=11;i++){
            str += arr[i];
        }
        str += " ";
        for(int i=12;i<=14;i++){
            str += arr[i];
        }
        return str;
}
    
}

class converter{
    public static String toLowerCase(String name){
        char arr[] = new char[name.length()];
        for(int i=0; i<name.length(); i++){
            arr[i] = name.charAt(i);
        }
        for(int i=0; i<name.length(); i++){
            if(arr[i]>='A' && arr[i]<='Z'){
                arr[i] = (char)(arr[i] + 32);
            }
        }    
        return String.valueOf(arr); 
   }

    public static String toUpperCase(String name){
     char arr[] = new char[name.length()];
     for(int i=0; i<name.length(); i++){
          arr[i] = name.charAt(i);
     }
     for(int i=0; i<name.length(); i++){
          if(arr[i]>='a' && arr[i]<='z'){
                arr[i] = (char)(arr[i] - 32);
          }
     }    
     return String.valueOf(arr);
    }

    public static int min(int a, int b){
        if(a<b){
            return a;
        }
        else{
            return b;
        }
    }
}


public class phoneBook{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int k = Integer.parseInt(scanner.nextLine());
        
        PhoneBookEntry[] phoneBook = new PhoneBookEntry[k];

        // Parse phone book entries
        for (int i = 0; i < k; i++) {
            String name = scanner.nextLine();
            name = converter.toLowerCase(name);
            String phoneNumber = scanner.nextLine();
            name = PhoneBookEntry.discard_name(name);
            phoneNumber = PhoneBookEntry.cleanPhoneNumber(phoneNumber);
            phoneBook[i] = new PhoneBookEntry(name, phoneNumber);
        }

     
        String searchString = scanner.nextLine();
        searchString = searchString.toLowerCase();


        //sort the phonebook
    for (int i = 0; i < phoneBook.length - 1; i++) {
       for (int j = 0; j < phoneBook.length - i - 1; j++) {
          if (PhoneBookEntry.compareStrings(phoneBook[j].name, phoneBook[j + 1].name) > 0) {
              PhoneBookEntry temp = phoneBook[j];
             phoneBook[j] = phoneBook[j + 1];
                phoneBook[j + 1] = temp;
        }
    }
  }

    //print the phonebook
        for (int i = 0; i < phoneBook.length; i++) {
            if (PhoneBookEntry.isMatch(phoneBook[i].name, searchString)) {
                System.out.println(phoneBook[i].name + " " +PhoneBookEntry.formatNumber(phoneBook[i].number) );
            }
        }


        scanner.close();
    }
}
