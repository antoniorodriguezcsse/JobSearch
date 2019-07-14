class Main {

    public static void main(String[] args) {


        BusinessObject bo = new BusinessObject();

        bo.setName("ddddddddddddddddddddddddddddddddddddddd");
// collect the constraint violations

    }

    public static class BusinessObject {

        private String car = "hello";

        public void setName( String bo)
        {
            System.out.println(bo);
        }
    }


}
