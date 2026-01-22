package com.example.quizzer;
import java.util.Collections;
import java.util.Stack;

public class TestManager {
    public enum Difficulty {
        Easy, Intermediate, Hard
    }
    private Difficulty difficulty;


    public Question CurrentQuestion;
    private int TotalScore=0;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getTotalScore(){
        return TotalScore;
    }

    public boolean Evaluate(int answer)
    {
        if(answer == CurrentQuestion.getCorrect_answer())
        {
            TotalScore+=(difficulty.ordinal()+1)*10;
            return true;
        }
        return false;
    }



    private Stack<Question> questions = new Stack<>();

    public TestManager(Stack<Question> all_questions, Difficulty difficulty) {
        this.difficulty = difficulty;
        Collections.shuffle(all_questions); // Shuffle all questions
        questions.addAll(all_questions.subList(0, Math.min(10, all_questions.size())));
        CurrentQuestion = questions.pop();
    }

    public Question GetQuestion() {
        CurrentQuestion=questions.pop();
        return CurrentQuestion;
    }

    public int RemainingQuestions() {
        return questions.size();
    }
}


class EasyQuestionManager extends TestManager {
    public EasyQuestionManager() {
        Stack<Question> questions = new Stack<>();
        questions.push(new Question("Can the main method in Java have any name?",new String[]{"Yes","No"},1)); //1
        questions.push(new Question("Is default value of a local variable in Java null?", new String[]{"Yes","No"},1)); //2
        questions.push(new Question("Is default value of a local variable in Java null?", new String[]{"Yes","No"},1)); //3
        questions.push(new Question("Which of the following is a valid Java identifier?",new String[]{"123variable","@name","_myVariable","void"},2));
        questions.push(new Question("What keyword is used to define a class in Java?",new String[]{"class","define"},0)); //4
        questions.push(new Question("Which of the following is a valid variable declaration in Java?",new String[]{"int num = 10;","integer num = 10;"},0)); //5
        questions.push(new Question("Which data type is used to store decimal numbers in Java?",new String[]{"float","int"},0)); //6
        questions.push(new Question("What is the default value of an uninitialized boolean variable in Java?",new String[]{"false","true"},0));//7
        questions.push(new Question("What is the result of 10 / 3 in Java if both numbers are integers?",new String[]{"3.33","3"},1));//8
        questions.push(new Question("Which operator is used for logical AND in Java?",new String[]{"&&","&"},0));//9
        questions.push(new Question("Which of these is used for incrementing a variable by 1?",new String[]{"x++","x+"},0));//10
        questions.push(new Question("What keyword is used to exit a loop early?",new String[]{"break","stop"},0));//11
        questions.push(new Question("What keyword is used to skip one iteration of a loop?",new String[]{"continue","skip"},0));//12
        questions.push(new Question("What keyword is used to define a method in Java?",new String[]{"void","function"},0));//13
        questions.push(new Question("What is the return type of a method that does not return anything?",new String[]{"void","null"},0));//14
        questions.push(new Question("How do you call a method named printMessage() from within the same class?",new String[]{"printMessage();","call printMessage();"},0));//14
        questions.push(new Question("What keyword is used to return a value from a method?",new String[]{"return","yield"},0));//15
        questions.push(new Question("What keyword is used to create an object in Java?",new String[]{"new","create"},0));//16
        questions.push(new Question("Can a class implement multiple interfaces in Java?",new String[]{"No","Yes"},1));//17
        questions.push(new Question("Which Java collection stores key-value pairs?",new String[]{"ArrayList","HashMap"},1));//18
        questions.push(new Question("What data structure uses a Last In, First Out (LIFO) order?",new String[]{"Queue","Stack"},1));//19
        questions.push(new Question("What block is always executed, whether an exception occurs or not?",new String[]{"finally","lastly"},0));//20
        questions.push(new Question("What is the correct extension for a Java source file?",new String[]{".java",".jav",".class",".exe"},0));
        questions.push(new Question("Which keyword is used to define a constant variable in Java?",new String[]{"static","const","constant","final"},3));
        questions.push(new Question("Which symbol is used for comparison in Java?",new String[]{"!=","=","==","<>"},2));
        questions.push(new Question("Which operator is used for logical OR in Java?",new String[]{"&&","||","or","|"},1));
        questions.push(new Question("What will 5 + 2 * 3 evaluate to in Java?",new String[]{"21","17","11","13"},2));
        questions.push(new Question("Which loop will always execute at least once, even if the condition is false?",new String[]{"for","while","do-while","for-each"},2));
        questions.push(new Question("What is the correct syntax to create an if-else statement in Java?",new String[]{"if (condition) { } else { }","if (condition) : else { }","if {condition} else","if condition else"},0));
        questions.push(new Question("Which of the following is used to skip a single iteration of a loop?",new String[]{"continue","exit","break","skip"},0));
        questions.push(new Question("What is the correct way to call a method named addNumbers() in the same class?",new String[]{"addNumbers();","call addNumbers();","invoke addNumbers();","run addNumbers();"},0));
        questions.push(new Question("What is the return type of the toString() method in Java?",new String[]{"Object","int","void","String"},3));

        super(questions,Difficulty.Easy);
    }
}

class IntermediateQuestionManager extends TestManager {
    public IntermediateQuestionManager() {

        Stack<Question> questions = new Stack<>();
        questions.push(new Question("int x = 10;\n" + "int y = 5;\n" + "if (x > y) {" + "x = y;}",new String[]{"x = 10","x = 5","x = 0","x = 15"},1)); //1
        questions.push(new Question("Which of the following is used to handle exceptions in Java?", new String[]{"throw","catch"},0)); //2
        questions.push(new Question("Which of the following collections is thread-safe in Java?", new String[]{"Vector","ArrayList"},0)); //3
        questions.push(new Question("What is the default value of a boolean variable in Java?",new String[]{"false","null"},0));
        questions.push(new Question("Which of the following methods is used to start a thread in Java?",new String[]{"thread.start();","thread.run();"},0)); //4
        questions.push(new Question("Which of the following collections maintains insertion order?",new String[]{"HashMap","LinkedHashMap"},1)); //5
        questions.push(new Question("Which keyword is used to achieve inheritance in Java?",new String[]{"extends","implements"},0)); //6
        questions.push(new Question("Which class is used to implement thread synchronization in Java?",new String[]{"Object","Thread"},1));//7
        questions.push(new Question("Which of the following can be a superclass of a class in Java?",new String[]{"Object","Base (if not defined)"},0));//8
        questions.push(new Question("Which keyword makes a method unchangeable?",new String[]{"final","static"},0));//9
        questions.push(new Question("What is the default value of an int in Java?",new String[]{"0","null"},0));//10
        questions.push(new Question("Which access modifier allows access from anywhere?",new String[]{"public","private"},0));//11
        questions.push(new Question("What is method overloading based on?",new String[]{"Parameters","Return type"},0));//12
        questions.push(new Question("What is the superclass of all classes?",new String[]{"Base","Object"},1));//13
        questions.push(new Question("Which keyword is used to call a superclass constructor?",new String[]{"this","super"},1));//14
        questions.push(new Question("Can a constructor be final?",new String[]{"Yes","No"},1));//14
        questions.push(new Question("Which class cannot be instantiated?",new String[]{"Static","Abstract"},1));//15
        questions.push(new Question("Which memory area stores objects?",new String[]{"Heap"," Stack"},0));//16
        questions.push(new Question("What exception occurs when dividing by zero?",new String[]{"NullPointerException","ArithmeticException"},1));//17
        questions.push(new Question("Which block is always executed?",new String[]{"catch","finally"},1));//18
        questions.push(new Question("Which collection does NOT allow duplicates?",new String[]{"List","Set","Queue","Map"},1));//19
        questions.push(new Question("What does this refer to?",new String[]{"Current object","Parent class","Superclass method","Static method"},0));//20
        questions.push(new Question("Which class is used for reading user input?",new String[]{"Scanner","Reader","Input","Console"},0));
        questions.push(new Question("Which access modifier makes a method visible only in its class?",new String[]{"default","public","protected","private"},3));
        questions.push(new Question("What is the return type of a constructor?",new String[]{"Object","void","None","class"},2));
        questions.push(new Question("Which keyword is used to create an interface?",new String[]{"abstract","interface","implements","class"},1));
        questions.push(new Question("Which method is called when an object is garbage collected?",new String[]{"destroy()","delete()","finalize()","remove()"},2));
        questions.push(new Question("What is method overriding based on?",new String[]{"Return type","Method name","Inheritance","Access level"},2));
        questions.push(new Question("Which type of exception must be handled?",new String[]{"Runtime","Unchecked","Checked","Error"},2));
        questions.push(new Question("What is the parent class of all exceptions?",new String[]{"Throwable","Error","Exception","RuntimeException"},0));
        questions.push(new Question("What does try do?",new String[]{"Handles exceptions","Defines a method","Declares a variable","Loops through data"},0));
        questions.push(new Question("Which method pauses a thread?",new String[]{"sleep()","stop()","wait()","halt()"},0));
        questions.push(new Question("Which keyword is used to stop thread execution temporarily?",new String[]{"pause()","exit()","halt()","wait()"},3));
        questions.push(new Question("Which of the following is a Java concurrency utility?",new String[]{"ExecutorService","ThreadPoolExecutor"},0));
        super(questions,Difficulty.Intermediate);
    }
}


class HardQuestionManager extends TestManager {
    public HardQuestionManager() {
        Stack<Question> questions = new Stack<>();
        questions.push(new Question("Can an interface have private methods?", new String[]{"Yes", "No"}, 0));
        questions.push(new Question("Can a static method be overridden?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Does Java support multiple inheritance?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Can a constructor be synchronized?", new String[]{"Yes", "No"}, 0));
        questions.push(new Question("Does Java support operator overloading?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Is 'null' a keyword in Java?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Can an enum extend another class?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Is StringBuffer thread-safe?", new String[]{"Yes", "No"}, 0));
        questions.push(new Question("Can a class be both abstract and final?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Does Java have function pointers like C?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Is it possible to override a private method?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Can an abstract class have a constructor?", new String[]{"Yes", "No"}, 0));
        questions.push(new Question("Is it mandatory to implement all methods of an interface?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Can a method local inner class access instance variables?", new String[]{"Yes", "No"}, 0));
        questions.push(new Question("Is it possible to serialize a static variable?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Does Java have multiple dispatch?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Can a Java class implement multiple interfaces?", new String[]{"Yes", "No"}, 0));
        questions.push(new Question("Is memory for static variables allocated on the heap?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Can a volatile variable guarantee atomicity?", new String[]{"Yes", "No"}, 1));
        questions.push(new Question("Does the Java compiler optimize final variables?", new String[]{"Yes", "No"}, 0));

        questions.push(new Question("Which of these is NOT a valid Java modifier?", new String[]{"final", "synchronized", "transient", "mutable"}, 3));
        questions.push(new Question("Which exception is thrown for an invalid array index?", new String[]{"ArrayIndexOutOfBoundsException", "NullPointerException", "IndexOutOfBoundsException", "ArithmeticException"}, 0));
        questions.push(new Question("Which keyword is used to prevent serialization?", new String[]{"final", "static", "private", "transient"}, 3));
        questions.push(new Question("Which memory area is used for method execution?", new String[]{"Heap", "Stack", "Method Area", "Metaspace"}, 1));
        questions.push(new Question("Which is NOT a valid Java functional interface?", new String[]{"Runnable", "Comparator", "Serializable", "Callable"}, 2));
        questions.push(new Question("Which method releases an object from the heap?", new String[]{"gc()", "finalize()", "dispose()", "clear()"}, 1));
        questions.push(new Question("Which is NOT a valid Java stream operation?", new String[]{"map()", "filter()", "reduce()", "group()"}, 3));
        questions.push(new Question("Which of the following prevents deadlocks?", new String[]{"Locks", "Synchronization", "Thread pooling", "Avoid nested locks"}, 3));
        questions.push(new Question("Which annotation is used for unit testing?", new String[]{"@Test", "@Unit", "@Run", "@Mock"}, 0));
        questions.push(new Question("Which of these collections is NOT synchronized?", new String[]{"Vector", "HashTable", "HashSet", "Stack"}, 2));
        questions.push(new Question("Which design pattern is used by Java’s Observer class?", new String[]{"Singleton", "Factory", "Observer", "Adapter"}, 2));
        questions.push(new Question("Which of these interfaces is NOT in java.util.concurrent?", new String[]{"Callable", "CompletableFuture", "ExecutorService", "Lock"}, 1));
        questions.push(new Question("What is the default thread priority in Java?", new String[]{"1", "5", "10", "Depends on JVM"}, 1));
        questions.push(new Question("Which of the following is NOT a valid lambda expression?", new String[]{"x -> x + 1", "() -> 5", "(x, y) -> x - y", "x, y -> x * y"}, 3));
        questions.push(new Question("Which method of Object class can be used for thread communication?", new String[]{"notify()", "wait()", "sleep()", "join()"}, 1));
        questions.push(new Question("Which of these can cause a deadlock?", new String[]{"Excessive garbage collection", "Nested synchronized blocks", "Multiple threads", "Infinite loops"}, 1));
        questions.push(new Question("Which of these can be used for parallel execution?", new String[]{"Threads", "Executors", "ForkJoinPool", "All of the above"}, 3));
        questions.push(new Question("Which collection type provides the best performance for retrieving elements?", new String[]{"LinkedList", "ArrayList", "HashSet", "TreeSet"}, 2));
        questions.push(new Question("Which keyword is used to define a record in Java?", new String[]{"class", "struct", "record", "data"}, 2));

        super(questions,Difficulty.Hard);
    }
}







