# Pubber (2019) - Pub tour Android application written in Java
<img src="https://user-images.githubusercontent.com/58415190/116613299-68ff0580-a930-11eb-846e-676ada048eb2.png" width="300" height="600">
Reads in pub information from database app is packaged with. 
<img src="https://user-images.githubusercontent.com/58415190/116613321-70261380-a930-11eb-8182-6ac72607b1fd.png" width="300" height="600">
<img src="https://user-images.githubusercontent.com/58415190/116613345-7916e500-a930-11eb-89b2-cdedbde8974c.png" width="300" height="600">
<img src="https://user-images.githubusercontent.com/58415190/116613358-7d430280-a930-11eb-8628-d449623447ca.png" width="300" height="600">
Pubs are sorted by the time the user wants to go to them.
```java
public class TimeComparator implements Comparator {
    @Override
    public int compare(Object p1, Object p2) {
        PubMiniCustomView k1 = (PubMiniCustomView) p1;
        PubMiniCustomView k2 = (PubMiniCustomView) p2;
        Pub o1 = k1.getPub();
        Pub o2 = k2.getPub();
        return (o1.getHour()*60 + o1.getMinute()) - (o2.getHour()*60 + o2.getMinute());
    }
}
```
