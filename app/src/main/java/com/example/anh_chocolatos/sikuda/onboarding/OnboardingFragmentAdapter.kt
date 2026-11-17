package com.example.anh_chocolatos.sikuda.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.anh_chocolatos.sikuda.OnboardingActivity

// Adapter ViewPager2 untuk halaman onboarding (seperti TutorialFragmentAdapter di materi)
class OnboardingFragmentAdapter(
    activity: OnboardingActivity,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
