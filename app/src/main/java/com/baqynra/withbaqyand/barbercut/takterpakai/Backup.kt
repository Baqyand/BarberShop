package com.baqynra.withbaqyand.barbercut.takterpakai

class Backup {
}
// backup
//                            adapter.registerAdapterDataObserver(object :
//                                RecyclerView.AdapterDataObserver() {
//                                override fun onChanged() {
//                                    super.onChanged()
//                                    checkEmpty()
//                                }
//
//                                override fun onItemRangeInserted(
//                                    positionStart: Int,
//                                    itemCount: Int
//                                ) {
//                                    super.onItemRangeInserted(positionStart, itemCount)
//                                    checkEmpty()
//                                }
//
//                                override fun onItemRangeRemoved(
//                                    positionStart: Int,
//                                    itemCount: Int
//                                ) {
//                                    super.onItemRangeRemoved(positionStart, itemCount)
//                                    checkEmpty()
//                                }
//
//                                fun checkEmpty() {
//                                    if (adapter.itemCount == 0 || adapter.itemCount < 1) {
//                                        rv_paket.visibility = View.GONE
//                                    } else {
//                                        rv_paket.visibility = View.VISIBLE
//                                    }
//
//                                }
//                            })

//    private fun inivisible() {
//        btn1.setOnClickListener{
//            if(collapsible.visibility== View.GONE){
//                collapsible.visibility= View.VISIBLE
//                dataarr.add(Paketpass("Cukur anak", 12000))
//                totalnya +=12000
//                parameterbtn1+=1
//                total.setText(library.toRupiah(totalnya))
//
//                plus.setOnClickListener {
//                    if(totalnya>99999999){
//                        Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
//                        totalnya = 99999999
//                        total.setText(library.toRupiah(totalnya))
//
////                total_bayar.setSelection(total_bayar.length())
//                    }else{
//                        dataarr.add(Paketpass("Cukur anak", 12000))
//                        totalnya +=12000
//                        parameterbtn1+=1
//                        total.setText(library.toRupiah(totalnya))
//                    }
//                }
//                minus.setOnClickListener {
//                    if(parameterbtn1 >=1 ){
//                        if(totalnya>0)
//                            totalnya-=12000
//                        parameterbtn1 -=1
//                        dataarr.removeLast()
//                        total.setText(library.toRupiah(totalnya))
//                    }
//
//                }
//            }else{
//                collapsible.visibility= View.GONE
////                if(parameterbtn1 >=1 ){
////                    if(totalnya>0)
////                        totalnya-=12000
////                    parameterbtn1 -=1
////                    dataarr.removeLast()
////                    total.setText(library.toRupiah(totalnya))
//            }
//        }
//
//        btn2.setOnClickListener{
//            if(a.visibility== View.GONE){
//                a.visibility= View.VISIBLE
//                dataarr.add(Paketpass("Cukur Dewasa", 15000))
//                totalnya+=15000
//                parameterbtn2 +=1
//                total.setText(library.toRupiah(totalnya))
//                ples.setOnClickListener {
//                    if(totalnya>99999999){
//                        Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
//                        totalnya= 99999999
//                        total.setText(library.toRupiah(totalnya))
////                total_bayar.setSelection(total_bayar.length())
//                    }else{
//                        dataarr.add(Paketpass("Cukur Dewasa", 15000))
//                        totalnya+=15000
//                        parameterbtn2 +=1
//                        total.setText(library.toRupiah(totalnya))
//                    }
//                }
//                min.setOnClickListener {
//                    if (parameterbtn2 >=1 ) {
//                        if (totalnya > 0)
//                            totalnya -= 15000
//                        parameterbtn2 -= 1
//                        dataarr.removeLast()
//                        total.setText(library.toRupiah(totalnya))
//                    }
//
//                }
//            }else{
//                a.visibility= View.GONE
//            }
//
//        }
//        btn3.setOnClickListener{
//            if(b.visibility== View.GONE){
//                b.visibility= View.VISIBLE
//                dataarr.add(Paketpass("Cuci Rambut", 3000))
//                totalnya += 3000
//                parameterbtn3 += 1
//                total.setText(library.toRupiah(totalnya))
//
//                tambah.setOnClickListener {
//                    if(totalnya>99999999){
//                        Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
//                        totalnya= 99999999
//                        total.setText(library.toRupiah(totalnya))
////                total_bayar.setSelection(total_bayar.length())
//                    }else{
//                        dataarr.add(Paketpass("Cuci Rambut", 3000))
//                        totalnya += 3000
//                        parameterbtn3 += 1
//                        total.setText(library.toRupiah(totalnya))
//                    }
//                }
//                kurang.setOnClickListener {
//                    if (parameterbtn3 >=1 )
//                        if(totalnya>0)
//                            totalnya-=3000
//                    parameterbtn3 -= 1
////                unit--
//                    total.setText(library.toRupiah(totalnya))
//                }
//
//            }else{
//                b.visibility= View.GONE
//            }
//
//        }
//        btn4.setOnClickListener{
//
//            if(c.visibility== View.GONE){
//                c.visibility= View.VISIBLE
//                dataarr.add(Paketpass("Cukur jenggot", 5000))
//                totalnya+=5000
//                parameterbtn4 += 1
//                total.setText(library.toRupiah(totalnya))
//
//                positif.setOnClickListener {
//                    if(totalnya>99999999){
//                        Snackbar.make(findViewById(android.R.id.content), "Nilai tidak boleh melebihi batas maksimum.", Snackbar.LENGTH_SHORT).show()
//                        totalnya= 99999999
//                        total.setText(library.toRupiah(totalnya))
////                total_bayar.setSelection(total_bayar.length())
//                    }else{
//                        dataarr.add(Paketpass("Cukur jenggot", 5000))
//                        totalnya+=5000
//                        parameterbtn4 += 1
//                        total.setText(library.toRupiah(totalnya))
//                    }
//                }
//                negatif.setOnClickListener {
//                    if (parameterbtn4 >= 1){
//                        if(totalnya>0)
//                            totalnya-=5000
//                        parameterbtn4 -= 1
//                        dataarr.removeLast()
//                        total.setText(library.toRupiah(totalnya))
//                    }
//
//                }
//            }else{
//                c.visibility= View.GONE
//            }
//
//        }
//    }