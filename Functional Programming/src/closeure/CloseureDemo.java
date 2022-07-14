package closeure;

@FunctionalInterface
interface Operation{
    void operate();
}
public class CloseureDemo {

//    In other words, a lambda expression becomes a closure
//    when it is able to access the variables that are outside of this scope.
//    It means a lambda can access its outer scope.
    public static void main(String[] args) {
        int x = 10;
        int y =20;

        doSum(x, new Operation() {
            @Override
            public void operate() {
//                can't change the value of the variable outside the object creation
//                this means that java doesn't support disclosure
//                y=30; compilation error
                System.out.println("Sum is "+(x+y));
            }
        });
    }

    static void doSum(int x,Operation operation){
        operation.operate();
    }
}
