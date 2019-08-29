package tat.mukhutdinov.deliveries.infrastructure.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import tat.mukhutdinov.deliveries.R
import tat.mukhutdinov.deliveries.infrastructure.model.DataState

@BindingAdapter("image")
fun setImage(image: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        image.setImageDrawable(ContextCompat.getDrawable(image.context, R.drawable.ic_account_box))
    } else {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_account_box)
            .into(image)
    }
}

@BindingAdapter("error")
fun setError(text: TextView, state: DataState) {
    text.text = when (state) {
        is DataState.Error -> state.message.orEmpty()
        DataState.Loaded -> ""
        else -> text.text
    }
}