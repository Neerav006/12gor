package com.bargor.samaj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberRegistration {

@SerializedName("part1")
@Expose
private Part1 part1;
@SerializedName("part2")
@Expose
private Part2 part2;
@SerializedName("part3")
@Expose
private Part3 part3;

public Part1 getPart1() {
return part1;
}

public void setPart1(Part1 part1) {
this.part1 = part1;
}

public Part2 getPart2() {
return part2;
}

public void setPart2(Part2 part2) {
this.part2 = part2;
}

public Part3 getPart3() {
return part3;
}

public void setPart3(Part3 part3) {
this.part3 = part3;
}

}