<template>
  <v-data-table
      :headers="this.headers"
      :items="this.items"
      :items-per-page="5"
      class="elevation-1"
  ></v-data-table>
</template>

<script>
export default {
name: "test",
  data() {
    return {
      items: [
        {"author": "Tianqi Chen", "nums": "65", "region": "US", "institute": "Carnegie Mellon University", "citation": "29377"},
        {"author": "Thierry Moreau", "nums": "27", "region": "US", "institute": "University of Washington", "citation": "1841"},
        {"author": "Ziheng Jiang", "nums": "16", "region": "US", "institute": "University of Washington", "citation": "1338"},
        {"author": "Lianmin Zheng", "nums": "11", "region": "US", "institute": "UC Berkeley", "citation": "1469"},
        {"author": "Eddie Yan", "nums": "17", "region": "US", "institute": "University of Washington", "citation": "1881"},
        {"author": "Haichen Shen", "nums": "37", "region": "US", "institute": "Scroll Tech", "citation": "1853"},
        {"author": "Meghan Cowan", "nums": "12", "region": "US", "institute": "Microsoft Research", "citation": "837"},
        {"author": "Leyuan Wang", "nums": "14", "region": "US", "institute": "University of California", "citation": "1207"},
        {"author": "Yuwei Hu", "nums": "8", "region": "US", "institute": "Cornell University", "citation": "1251"},
        {"author": "Luis Ceze", "nums": "315", "region": "US", "institute": "University of Washington", "citation": "14764"},
        {"author": "Carlos Guestrin", "nums": "253", "region": "US", "institute": "Stanford University", "citation": "62600"},
        {"author": "Arvind Krishnamurthy", "nums": "267", "region": "US", "institute": "University of Washington", "citation": "21320"},
        {"author": "Huan Wang", "nums": "19", "region": "US", "institute": "Northeastern University", "citation": "426"},
        {"author": "Adam Paszke", "nums": "16", "region": "Poland", "institute": "Google", "citation": "27893"},
        {"author": "Sam Gross", "nums": "21", "region": "US", "institute": "Facebook AI Research", "citation": "28319"},
        {"author": "Francisco Massa", "nums": "18", "region": "US", "institute": "Facebook AI Research", "citation": "20278"},
        {"author": "Adam Lerer", "nums": "53", "region": "US", "institute": "Facebook AI Research", "citation": "28139"},
        {"author": "James Bradbury", "nums": "17", "region": "US", "institute": "Google", "citation": "19931"},
        {"author": "Zeming Lin", "nums": "19", "region": "US", "institute": "New York University", "citation": "26202"},
        {"author": "Lu Fang", "nums": "16", "region": "US", "institute": "Facebook AI Research", "citation": "16237"},
        {"author": "Junjie Bai", "nums": "3", "region": "CHN", "institute": "Alibaba", "citation": "15434"},
        {"author": "Aravind Acharya", "nums": "11", "region": "US", "institute": "Nvidia Corporation", "citation": "138"},
        {"author": "Uday Reddy Bondhugula", "nums": "56", "region": "INDIA", "institute": "Indian institute of Science", "citation": "4743"},
        {"author": "Albert Cohen", "nums": "524", "region": "US", "institute": "Universit\u8305 Paris VI", "citation": "18934"},
        {"author": "C\u8305dric Bastoul", "nums": "75", "region": "FRANCE", "institute": "University of Strasbourg", "citation": "3608"},
        {"author": "Louis-Noel Pouchet", "nums": "142", "region": "US", "institute": "Colorado State University", "citation": "4946"},
        {"author": "Bo-Yuan Huang", "nums": "15", "region": "US", "institute": "Intel Corporation", "citation": "142"},
        {"author": "Mike He", "nums": "3", "region": "US", "institute": "University of Washington", "citation": "12"},
        {"author": "Li Lyna Zhang", "nums": "16", "region": "CHN", "institute": "Microsoft Research Asia", "citation": "231"},
        {"author": "Yang Wang", "nums": "11", "region": "CHN", "institute": "University of Electronic Science and Technology of China", "citation": "94"},
        {"author": "Mao Yang", "nums": "48", "region": "CHN", "institute": "Microsoft Research Asia", "citation": "1902"},
        {"author": "Xuyi Cai", "nums": "6", "region": "CHN", "institute": "Chinese Academy of Sciences", "citation": "31"},
        {"author": "Fuxun Yu", "nums": "37", "region": "US", "institute": "George Mason University", "citation": "491"},
        {"author": "Di Wang", "nums": "71", "region": "US", "institute": "Microsoft Research", "citation": "1968"},
        {"author": "Chenchen Liu", "nums": "56", "region": "US", "institute": "University of Maryland", "citation": "907"},
        {"author": "Sheng-Chun Kao", "nums": "17", "region": "US", "institute": "Georgia institute of Technology", "citation": "83"},
        {"author": "Tushar Krishna", "nums": "153", "region": "US", "institute": "Georgia institute of Technology", "citation": "11289"},
        {"author": "Saurabh Raje", "nums": "11", "region": "US", "institute": "University of Utah", "citation": "35"},
        {"author": "Atanas Rountev", "nums": "143", "region": "US", "institute": "The Ohio State University", "citation": "6434"},
        {"author": "Zhen ZHENG", "nums": "16", "region": "CHN", "institute": "Alibaba", "citation": "138"},
        {"author": "Xuanda Yang", "nums": "3", "region": "US", "institute": "University of California, San Diego", "citation": "5"},
        {"author": "Pengzhan Zhao", "nums": "5", "region": "US", "institute": "University of California, Los Angeles", "citation": "70"},
        {"author": "Wei Niu", "nums": "39", "region": "US", "institute": "William & Mary", "citation": "344"},
        {"author": "Xiaolong Ma", "nums": "63", "region": "US", "institute": "Northeastern University", "citation": "1061"},
        {"author": "Sheng Lin", "nums": "28", "region": "US", "institute": "Tencent America", "citation": "599"},
        {"author": "Xuehai Qian", "nums": "107", "region": "US", "institute": "University of Southern California", "citation": "2974"},
        {"author": "Xue Lin", "nums": "166", "region": "US", "institute": "Northeastern University", "citation": "4239"},
        {"author": "Peiyan Dong", "nums": "17", "region": "US", "institute": "Northeastern University", "citation": "48"},
        {"author": "Siyue Wang", "nums": "21", "region": "US", "institute": "Northeastern University", "citation": "355"},
        {"author": "pu zhao", "nums": "50", "region": "US", "institute": "Northeastern University", "citation": "643"},
        {"author": "Zheng Zhan", "nums": "17", "region": "US", "institute": "Northeastern University", "citation": "60"},
        {"author": "Yanzhi Wang", "nums": "659", "region": "US", "institute": "Northeastern University", "citation": "10897"},
        {"author": "Matthias Boehm", "nums": "83", "region": "Austria", "institute": "Graz University of Technology", "citation": "1350"},
        {"author": "Berthold Reinwald", "nums": "138", "region": "US", "institute": "IBM Research", "citation": "5008"},
        {"author": "Shana Hutchison", "nums": "20", "region": "US", "institute": "University of Washington", "citation": "669"},
        {"author": "Prashanth Menon", "nums": "16", "region": "US", "institute": "Carnegie Mellon University", "citation": "512"},
        {"author": "Todd C. Mowry", "nums": "160", "region": "US", "institute": "Carnegie Mellon University", "citation": "12000"},
        {"author": "Andy Pavlo", "nums": "93", "region": "US", "institute": "Carnegie Mellon University", "citation": "7403"},
        {"author": "Pratik Fegade", "nums": "3", "region": "US", "institute": "Carnegie Mellon University", "citation": "12"},
        {"author": "Phillip Gibbons", "nums": "317", "region": "US", "institute": "Carnegie Mellon University", "citation": "29294"},
        {"author": "Kathryn S McKinley", "nums": "303", "region": "US", "institute": "Google", "citation": "17004"}
      ],
      headers: [
        {
          text: "author",
          value: 'author'
        },
        {
          text: "nums",
          value: 'nums'
        },
        {
          text: "region",
          value: "region"
        },
        {
          text: "institute",
          value: "institute"
        },
        {
          text: "citation",
          value: "citation"
        }
      ]
    }
  }
}
</script>

<style scoped>

</style>