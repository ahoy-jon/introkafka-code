package fold;


import java.util.Arrays;
import java.util.List;

public class BasicConsumer {

    public static class SumConsume implements Consume<Integer, Integer> {

        private Integer s = 0;

        @Override
        public Integer consume(Integer i) {
            s = s + i;
            return s;
        }
    }

    public static class CountConsume implements Consume<Integer, Integer> {

        private Integer c = 0;

        @Override
        public Integer consume(Integer integer) {
            c++;
            return c;
        }
    }


    private static class MeanConsumeLogic implements Consume<Tuple2<Integer, Integer>, Double> {
        @Override
        public Double consume(Tuple2<Integer, Integer> integerIntegerTuple2) {
            return integerIntegerTuple2._1.doubleValue() / integerIntegerTuple2._2;
        }
    }

    static <A,B,C,D> Consume<A,D> composeConsumer(final Consume<A,B> i1, final Consume<A,C> i2, final Consume<Tuple2<B,C>, D> o) {
        return new Consume<A, D>() {
            @Override
            public D consume(final A a) {
                final B bo = i1.consume(a);
                final C co = i2.consume(a);
                return o.consume(new Tuple2<B, C>(bo, co));
            }
        };
    }



    public static void main(String[] args) {
        final List<Integer> integers = Arrays.asList(1, 2, 3, -3, 0, 9);

        final Consume<Integer, Double> integerDoubleConsume = composeConsumer( new SumConsume()
        , new CountConsume(), new MeanConsumeLogic());


        final SumConsume sumConsume = new SumConsume();


  //      final Consume<Integer, Double> integerDoubleConsume1 = composeConsumer(new MovingSumConsume(), new MouvingCountConsume(), new MeanConsumeLogic());


        for(Integer i:integers) {
            System.out.println("------");
            System.out.println(sumConsume.consume(i));

            System.out.println(integerDoubleConsume.consume(i));


//          System.out.println(integerDoubleConsume1.consume(i));
        }


    }



    public static class MovingSumConsume implements Consume<Integer, Integer> {


        private Integer p0 = 0;
        private Integer p1 = 0;
        private Integer p2 = 0;

        @Override
        public Integer consume(Integer i) {
            p2 = p1;
            p1 = p0;
            p0 = i;

            return p0 + p1 + p2;
        }
    }

    public static class MouvingCountConsume implements Consume<Integer, Integer> {

        private Integer c = 0;

        @Override
        public Integer consume(Integer integer) {
            c++;

            if(c > 3) {
                c=3;
            }
            return c;
        }
    }
}
