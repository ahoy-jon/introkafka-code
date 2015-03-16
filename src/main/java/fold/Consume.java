package fold;


public interface Consume<A,B> {
    B consume(A a);
}