
//Assignment 6: Exception HandlingAssignment 6: Exception Handling

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class person{
   private String firstName;
   private String middleName;
   private String lastName;
   private  String email;
   private  String nid;
   private String passport;
   private String birthDate;
   private String addressP1;
   private String addressP2;
   private String addressP3;

   person(){
         this.firstName = "";
         this.middleName = "";
         this.lastName = "";
         this.email = "";
         this.nid = "";
         this.passport = "";
         this.birthDate = "";
         this.addressP1 = "";
         this.addressP2 = "";
         this.addressP3 = "";
    }
    
     void setName(String firstName, String middleName, String lastName){
          this.firstName = firstName;
          this.middleName = middleName;
          this.lastName = lastName;
     }
    
     void setEmail(String email){
          this.email = email;
     }
    
     void setNid(String nid){
          this.nid = nid;
     }
    
     void setPassport(String passport){
          this.passport = passport;
     }
    
     void setBirthDate(String birthDate){
          this.birthDate = birthDate;
     }
    
     void setAddress(String addressP1, String addressP2, String addressP3){
          this.addressP1 = addressP1;
          this.addressP2 = addressP2;
          this.addressP3 = addressP3;
     }
    
     String getName(){
          return this.firstName+" "+this.middleName+" "+this.lastName;
     }
     String getFirstName(){
          return this.firstName;
     }
     String getMiddleName(){
          return this.middleName;
        }
        String getLastName(){
            return this.lastName;
        }
    
     String getEmail(){
          return this.email;
     }
    
     String getNid(){
          return this.nid;
     }
    
     String getPassport(){
          return this.passport;
     }
    
     String getBirthDate(){
          return this.birthDate;
     }
    
     String getAddress(){
          return this.addressP1+" "+this.addressP2+" "+this.addressP3;
     
   }


    
}
class FirstNameAbsenceException extends Exception {
    public FirstNameAbsenceException(String message) {
        super(message);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}

class LastNameAbsenceException extends Exception {
    public LastNameAbsenceException(String message) {
        super(message);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}

class NoNamePresentException extends Exception {
    public NoNamePresentException(String message) {
        super(message);
    }
    @Override 
    public String toString() {
        return super.toString();
    }
}

class BlankEmailFieldExceeption extends Exception{
    public BlankEmailFieldExceeption(String message){
        super(message);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}
class AbsenceofGmailSuffixException extends Exception{
    public AbsenceofGmailSuffixException(String message){
        super(message);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}
class NotProperlyFormatedEmailPrefixException extends Exception{
    public NotProperlyFormatedEmailPrefixException(String message){
        super(message);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}
class BlankNIDPassportFieldException extends Exception{
    public BlankNIDPassportFieldException(String message){
        super(message);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}
class UnexpectedNIDFormatException extends Exception{
    public UnexpectedNIDFormatException(String message){
        super(message);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}
class UnexpectedPassportFormatException extends Exception{
    public UnexpectedPassportFormatException(String message){
        super(message);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}

class BirthdateBlankException extends Exception{
    public BirthdateBlankException(String message){
        super(message);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}

class WrongBirthdateFormatException extends Exception{
    public WrongBirthdateFormatException(String message){
        super(message);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}

class AddressBlankException extends Exception{
    public AddressBlankException(String message){
        super(message);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}

class WrongAddressFormatException extends Exception{
    public WrongAddressFormatException(String message){
        super(message);
    }
    @Override
    public String toString(){
        return super.toString();
    }
}




class inputValidation{
    static void validateName(String firstName, String middleName, String lastName) throws FirstNameAbsenceException, LastNameAbsenceException, NoNamePresentException{

        if(firstName == "" && lastName == ""){
            throw new NoNamePresentException(" No name is present");
        }
       if(firstName == ""){
           throw new FirstNameAbsenceException(" First name is missing");
         }
        if(lastName == ""){
            throw new LastNameAbsenceException(" Last name is missing");
        }
    }

    static void validateEmail(String email) throws BlankEmailFieldExceeption, AbsenceofGmailSuffixException, NotProperlyFormatedEmailPrefixException{
        if(email == "" || email == null){
            throw new BlankEmailFieldExceeption("Email field is blank");
        }
        Pattern pattern = Pattern.compile("[a-z0-9]+@gmail.com");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new NotProperlyFormatedEmailPrefixException("Email address is not properly formatted");
        }
        if(!email.contains("@gmail.com")){
            throw new AbsenceofGmailSuffixException("Email address does not contain gmail suffix");
        }
    }
    static void validateNIDPassport(String nid, String passport) throws BlankNIDPassportFieldException, UnexpectedNIDFormatException, UnexpectedPassportFormatException{
      
        if (nid != null && !nid.matches("\\d{11}")&& (passport == "" || passport == null)) {
            throw new UnexpectedNIDFormatException("Unexpected NID format");
        }
        if (passport != null && !passport.matches("[A-Z]{2}\\d{7}")&& (nid == "" || nid == null)) {
            throw new UnexpectedPassportFormatException("Unexpected Passport format");
        }  if((nid == "" || nid == null) && (passport == "" || passport == null)){
            throw new BlankNIDPassportFieldException("NIDPassport field is blank");
        }
        if(nid != null && !nid.matches("\\d{11}") && passport != null && !passport.matches("[A-Z]{2}\\d{7}")){
            throw new UnexpectedNIDFormatException("Unexpected NIDPassport format");
        }
    }

    static void validateBirthDate(String birthDate) throws BirthdateBlankException, WrongBirthdateFormatException{
        if(birthDate == "" || birthDate == null){
            throw new BirthdateBlankException("Birthdate field is blank");
        }
        if (!birthDate.matches("\\d{2} (January|| February||March||April||May||June||July||August||September||October||November||December) \\d{4}")) {
            throw new WrongBirthdateFormatException("Wrong Birthdate format");
        }
    }

    static void validateAddress(String addressP1, String addressP2, String addressP3) throws AddressBlankException, WrongAddressFormatException{
        if(addressP1 == "" || addressP1 == null){
            throw new AddressBlankException("Address field p1 is blank");
        }
        if(addressP2 == "" || addressP2 == null){
            throw new AddressBlankException("Address field p2 is blank");
        }
        if(addressP3 == "" || addressP3 == null){
            throw new AddressBlankException("Address field p3 is blank");
        }
        if (!addressP1.matches("[a-zA-Z0-9/]+")) {
            throw new WrongAddressFormatException("Wrong P1 Address format");
        }
        
        if(!addressP2.matches("[a-zA-Z]+")){
            throw new WrongAddressFormatException("Wrong P2 Address format");
        }
        if (!addressP3.matches("(Dhaka|Chottogram|Barishal|Khulna|Sylhet|Rajshahi|Mymensingh|Rangpur)")) {
            throw new WrongAddressFormatException("Wrong P3 Address format");
        }
    }

}


public class inputValidations{
    
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);

        List<person> persons = new ArrayList<person>();
        System.out.println(" ************  Part 1: Input Validation  ************");
        System.out.println("Enter the number of persons to input: ");
        int numPersons = sc.nextInt();
        sc.nextLine(); 

    for(int i=0; i<numPersons; i++){   

    System.out.println(" ************   Enter details for person " + (i + 1) + ":  *************"); 
        person p = new person();

// handle name exception
       try{
        System.out.println("Enter First Name:");
        String firstName = sc.nextLine();
        System.out.println("Enter Middle Name:");
        String middleName = sc.nextLine();
        System.out.println("Enter Last Name:");
        String lastName = sc.nextLine();
        p.setName(firstName, middleName, lastName);
       inputValidation.validateName(firstName, middleName,lastName);

       }catch(FirstNameAbsenceException e){
            System.out.println(e);
        }
        catch(LastNameAbsenceException e){
            System.out.println(e);
        }
        catch(NoNamePresentException e){
            System.out.println(e);
        }
//handle name exception


// handle email exception
        try{
            System.out.println("Enter Email: Format(xyz55@gmail.com)");
            String email = sc.nextLine();
            p.setEmail(email);
            inputValidation.validateEmail(email);
        }catch(BlankEmailFieldExceeption e){
            System.out.println(e);

        }catch(AbsenceofGmailSuffixException e){
            System.out.println(e);
        }catch(NotProperlyFormatedEmailPrefixException e){
            System.out.println(e);
        }
// handle email exception

// handle nid passport exception
        try{
            System.out.println("Enter NID or Passport or Both:");
            System.out.println();
            System.out.println("Enter NID: Format(1234xxxxxxx):");
            String nid = sc.nextLine();
            System.out.println("Enter Passport: Format(AB1234567):");
            String passport = sc.nextLine();
            p.setNid(nid);
            p.setPassport(passport);
            inputValidation.validateNIDPassport(nid, passport);
        }
        catch(BlankNIDPassportFieldException e){
            System.out.println(e);
        }
        catch(UnexpectedNIDFormatException e){
            System.out.println(e);
        }
        catch(UnexpectedPassportFormatException e){
            System.out.println(e);
        }
// handle nid passport exception

// handle birthdate exception
        try{
            System.out.println("Enter Birth Date: Formate(22 May 2002):");
            String birthDate = sc.nextLine();
            p.setBirthDate(birthDate);
            inputValidation.validateBirthDate(birthDate);
        }
        catch(BirthdateBlankException e){
            System.out.println(e);
        }
        catch(WrongBirthdateFormatException e){
            System.out.println(e);
        }
// handle birthdate exception


// handle address exception
        try{
            System.out.println("Address:");
            System.out.println("Address Part 1: Format(123/A)");
            String addressP1 = sc.nextLine();
            System.out.println("Address Part 2: Format(Dhanmondi)");
            String addressP2 = sc.nextLine();
            System.out.println("Address Part 3: Format(Dhaka)");
            String addressP3 = sc.nextLine();
            p.setAddress(addressP1, addressP2, addressP3);
            inputValidation.validateAddress(addressP1, addressP2, addressP3);
        }
        catch(AddressBlankException e){
            System.out.println(e);
        }
        catch(WrongAddressFormatException e){
            System.out.println(e);
        }

// handle address exception

// add person to list
        persons.add(p);

    }

// // print all persons
//     for(person p: persons){
//         System.out.println("Name: "+p.getName());
//         System.out.println("Email: "+p.getEmail());
//         System.out.println("NID: "+p.getNid());
//         System.out.println("Passport: "+p.getPassport());
//         System.out.println("Birth Date: "+p.getBirthDate());
//         System.out.println("Address: "+p.getAddress());
//     }

 // Part 2 Quering
    System.out.println(" ************** Part 2: Quering ***************");
    System.out.println("Enter the number of queries: ");
    int num = sc.nextInt();
    sc.nextLine();
    
    for(int i=0; i<num; i++){
       System.out.println("Enter the number of Nth person to query: ");
         int n = sc.nextInt(); 
        sc.nextLine();
        person p = persons.get(n-1);
        System.out.println("****************  Person "+n+" Details: ******************");
        try {
            inputValidation.validateName(p.getFirstName(), p.getMiddleName(), p.getLastName());
            System.out.println("First Name: " + p.getFirstName());
            System.out.println("Middle Name: " + p.getMiddleName());
            System.out.println("Last Name: " + p.getLastName());
        } 
        catch (FirstNameAbsenceException e) {
            System.out.println(e);
        } 
        catch (LastNameAbsenceException e) {
            System.out.println(e);
        } 
        catch (NoNamePresentException e) {
            System.out.println(e);
        }
        try {
            inputValidation.validateEmail(p.getEmail());
            System.out.println("Email: " + p.getEmail());
        } 
        catch (BlankEmailFieldExceeption e) {
            System.out.println(e);
        } 
        catch (AbsenceofGmailSuffixException e) {
            System.out.println(e);
        } 
        catch (NotProperlyFormatedEmailPrefixException e) {
            System.out.println(e);
        }
        try {
            inputValidation.validateNIDPassport(p.getNid(), p.getPassport());
            System.out.println("NID: " + p.getNid());
            System.out.println("Passport: " + p.getPassport());
        } 
        catch (BlankNIDPassportFieldException e) {
            System.out.println(e);
        } 
        catch (UnexpectedNIDFormatException e) {
            System.out.println(e);
        } 
        catch (UnexpectedPassportFormatException e) {
            System.out.println(e);
        }
        try {
            inputValidation.validateBirthDate(p.getBirthDate());
            System.out.println("Birth Date: " + p.getBirthDate());
        } 
        catch (BirthdateBlankException e) {
            System.out.println(e);
        } 
        catch (WrongBirthdateFormatException e) {
            System.out.println(e);
        }
        try {
            inputValidation.validateAddress(p.getAddress(), p.getAddress(), p.getAddress());
            System.out.println("Address: " + p.getAddress());
        } 
        catch (AddressBlankException e) {
            System.out.println(e);
        } 
        catch (WrongAddressFormatException e) {
            System.out.println(e);
        }

    }

}
}
