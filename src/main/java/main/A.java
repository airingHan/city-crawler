package main;

public class A {

    public static void main(String[] args) {
        String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/44/4419.html";
        int i = url.lastIndexOf("/");
        System.out.println(i);
        System.out.println(url.substring(i));
        System.out.println(url.substring(i+1, i + 5)+"00");
        System.out.println(url.substring(i+1, i + 5));
    }

}
